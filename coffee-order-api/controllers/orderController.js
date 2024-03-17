const Order = require("../models/order");
const User = require("../models/user");
const Product = require("../models/product");

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

const getProductObjs = async (products) => {
    const productPromises = products.map(async (p) => {
        const product = await Product.findOne({ _id: p.product });
        return { product: product._id, quantity: p.quantity };
    });
    return await Promise.all(productPromises);
};

const createOrder = async (req, res) => {
  try {
    const { client, products } = req.body;

    const clientExists = await User.findById({
        _id: client
    });

    if (!clientExists) {
        return res.status(404).json({
            error: true,
            success: false,
            message: `Client with id: ${client} not found`
        });
    }

    const productObjs = await getProductObjs(products);

    const newOrder = await new Order({ client: clientExists._id, products: productObjs }).save();

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

module.exports = { getAllOrders, getOrderByID, getOrderByUser, createOrder };
