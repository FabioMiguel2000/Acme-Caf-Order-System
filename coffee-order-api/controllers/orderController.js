const Order = require("../models/order");
const User = require("../models/user");
const Product = require("../models/product");
const {
  updateUserAccumulatedCoffeeBuys,
  updateUserAccumulatedExpenses,
} = require("./userController");
const { validateVoucher, useVoucher } = require("./voucherController");

const getAllOrders = async (req, res) => {
  try {
    const orders = await Order.find();
    return res.status(200).json({
      success: true,
      message: `Retrieved ${orders.length} orders`,
      data: orders,
    });
  } catch (error) {
    return res.status(500).json({
      error: true,
      success: false,
      message: "Failed to retrieve orders",
    });
  }
};

const getOrderByID = async (req, res) => {
  try {
    const { id } = req.params;
    const order = await Order.findById(id);
    if (!order) {
      return res.status(404).json({
        error: true,
        success: false,
        message: `Order with id: ${id} not found`,
      });
    }
    return res.status(200).json({
      success: true,
      message: `Retrieved order with id: ${id}`,
      data: order,
    });
  } catch (error) {
    return res.status(500).json({
      error: true,
      success: false,
      message: "Failed to retrieve order",
    });
  }
};

const getOrderByUser = async (req, res) => {
  try {
    const { client } = req.query;
    const orders = await Order.find({ client });
    return res.status(200).json({
      success: true,
      message: `Retrieved ${orders.length} orders from client: ${client}`,
      data: orders,
    });
  } catch (error) {
    return res.status(500).json({
      error: true,
      success: false,
      message: "Failed to retrieve orders",
    });
  }
};

const getProductObjs = async (products, isName = false) => {
  const productPromises = products.map(async (p) => {
    const product = await Product.findOne(
      isName ? { name: p.product } : { _id: p.product }
    );
    return { product: product, quantity: p.quantity };
  });
  return await Promise.all(productPromises);
};

const calculatePrices = (products, discountVoucher) => {
  let subtotal = 0;
  let total = 0;
  products.forEach((p) => {
    subtotal += p.product.price * p.quantity;
  });
  if (discountVoucher) {
    total = subtotal * 0.95;
  } else {
    total = subtotal;
  }

  const promotionDiscount = total - subtotal;

  return { subtotal, promotionDiscount, total };
};

const countCups = (products, freeCoffeeVoucher) => {
  let count = 0;
  products.forEach((p) => {
    count += p.quantity;
  });
  if (freeCoffeeVoucher) {
    return count - 1;
  }
  return count;
};

const createOrder = async (req, res) => {
  try {
    const { client, products, discountVoucher, freeCoffeeVoucher } = req.body;

    const clientExists = await User.findById({
      _id: client,
    });

    if (!clientExists) {
      return res.status(404).json({
        error: true,
        success: false,
        message: `Client with id: ${client} not found`,
      });
    }

    const productObjs = await getProductObjs(products);

    const vDiscountVoucher = await validateVoucher(discountVoucher, client);
    const vFreeCoffeeVoucher = await validateVoucher(freeCoffeeVoucher, client);

    const { subtotal, promotionDiscount, total } = calculatePrices(
      productObjs,
      vDiscountVoucher
    );

    // Order Execution and Database Update
    if (vDiscountVoucher){
        await useVoucher(vDiscountVoucher);
    }
    if (vFreeCoffeeVoucher){
        await useVoucher(vFreeCoffeeVoucher);
    }

    await updateUserAccumulatedCoffeeBuys(
      clientExists._id,
      countCups(products, vFreeCoffeeVoucher)
    );
    await updateUserAccumulatedExpenses(clientExists._id, total);

    const newOrder = await new Order({
      client: clientExists._id,
      products: productObjs,
      subtotal,
      promotionDiscount,
      total,
      discountVoucher: vDiscountVoucher,
      freeCoffeeVoucher: vFreeCoffeeVoucher,
    }).save();

    return res.status(201).json({
      success: true,
      message: `Order created with id: ${newOrder._id}`,
      data: newOrder,
    });
  } catch (error) {
    return res.status(500).json({
      error: true,
      success: false,
      message: `Failed to create order: ${error}`,
    });
  }
};

const createOrderByProductNames = async (order) => {
  try {
    const { client, products } = order;

    const clientExists = await User.findOne({
      nif: client,
    });

    if (!clientExists) {
      throw new Error(`Client with id: ${client} not found`);
    }

    const productObjs = await getProductObjs(products, true);

    const { subtotal, promotionDiscount, total } = calculatePrices(
      productObjs,
      clientExists.discountVoucher
    );

    await updateUserAccumulatedCoffeeBuys(
      clientExists._id,
      countCups(products, false)
    );
    await updateUserAccumulatedExpenses(clientExists._id, total);

    await new Order({
      client: clientExists._id,
      products: productObjs,
      subtotal,
      promotionDiscount,
      total,
    }).save();
  } catch (error) {
    throw new Error(`Failed to create order: ${error}`);
  }
};

module.exports = {
  getAllOrders,
  getOrderByID,
  getOrderByUser,
  createOrder,
  createOrderByProductNames,
};
