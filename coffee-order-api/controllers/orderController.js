const Order = require("../models/order");
const User = require("../models/user");
const Product = require("../models/product");
const {
  updateUserAccumulatedCoffeeBuys,
  updateUserAccumulatedExpenses,
} = require("./userController");
const { validateVoucher, useVoucher } = require("./voucherController");
const { returnResponse } = require("../services/general");

const getAllOrders = async (req, res) => {
  try {
    const orders = await Order.find();
    returnResponse(res, 200, true, `Retrieved ${orders.length} orders`, orders);
  } catch (error) {
    return returnResponse(res, 500, false, `Failed to retrieve orders`);
  }
};

const getOrderByID = async (req, res) => {
  try {
    const { id } = req.params;
    if(id){
      const order = await Order.findById(id);
      if (!order) {
        return returnResponse(res, 404, false, `Order with id: ${id} not found`);
      }
      returnResponse(res, 200, true, `Retrieved order with id: ${id}`, order);
    } else {
      return returnResponse(res, 400, false, `Invalid id: ${id}`);
    }
  } catch (error) {
    return returnResponse(res, 500, false, `Failed to retrieve order`);
  }
};

const getOrderByUser = async (req, res) => {
  try {
    const { client } = req.query;
    const orders = await Order.find({ client });

    return returnResponse(res, 200, true, `Retrieved ${orders.length} orders from client: ${client}`, orders);

  } catch (error) {
    return returnResponse(res, 500, false, `Failed to retrieve orders`);
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

  const promotionDiscount = Math.round((total - subtotal) * 100) / 100;

  subtotal = Math.round(subtotal * 100) / 100;
  total = Math.round(total * 100) / 100;

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

const validateOrder = async (req, res) => {
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

    if (order.status !== "Pending") {
      return returnResponse(res, 400, false, `Order with id: ${id} is not pending`);
    }

    if (order.discountVoucher) {
      await useVoucher(order.discountVoucher);
    }
    if (order.freeCoffeeVoucher) {
      await useVoucher(order.freeCoffeeVoucher);
    }

    await updateUserAccumulatedCoffeeBuys(
      order.client,
      countCups(order.products, order.freeCoffeeVoucher)
    );
    await updateUserAccumulatedExpenses(order.client, order.total);

    order.status = "Verified";
    await order.save();
    
    return returnResponse(res, 201, true, `Order with id: ${id} verified`, order);
    
  } catch (error) {
    return returnResponse(res, 500, false, `Failed to validate order: ${error}`);
  }
};

const createOrder = async (req, res) => {
  try {
    const { client, products, discountVoucher, freeCoffeeVoucher, status } =
      req.body;

    if (status !== "Pending") {
      return returnResponse (res, 400, false, `Invalid status: ${status}`);
    }

    const clientExists = await User.findById({
      _id: client,
    });

    if (!clientExists) {
      return returnResponse(res, 404, false, `Client with id: ${client} not found`);
    }

    const productObjs = await getProductObjs(products);

    const vDiscountVoucher = await validateVoucher(discountVoucher, client);
    const vFreeCoffeeVoucher = await validateVoucher(freeCoffeeVoucher, client);

    const freeCoffeeProductExist = productObjs
      .map((p) => p.product.name)
      .includes("Free Coffee");

    if (
      (vFreeCoffeeVoucher && !freeCoffeeProductExist) ||
      (!vFreeCoffeeVoucher && freeCoffeeProductExist)
    ) {

      return returnResponse(res, 400, false, `Free Coffee Voucher not valid or Free Coffee product not in order`);

    }

  
    const { subtotal, promotionDiscount, total } = calculatePrices(
      productObjs,
      vDiscountVoucher
    );

    const newOrder = await new Order({
      client: clientExists._id,
      products: productObjs,
      subtotal,
      promotionDiscount,
      total,
      discountVoucher: vDiscountVoucher,
      freeCoffeeVoucher: vFreeCoffeeVoucher,
    }).save();

    return returnResponse(res, 201, true, `Order created with id: ${newOrder._id}`, newOrder);

  } catch (error) {
    return returnResponse(res, 500, false, `Failed to create order: ${error}`);
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
  validateOrder,
};
