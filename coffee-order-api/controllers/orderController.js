const Order = require("../models/order");

const getAllOrders = async (req, res) => {
  try {
    const orders = await Order.find();
    return res.status(200).json({
      success: true,
      message: `Retrieved ${orders.length} orders`,
      data: orders,
    });
  } catch (error) {
    console.log(error);
    return res.status(500).json({
      error: true,
      success: false,
      message: "Failed to retrieve orders",
    });
  }
};

const getSingleOrder = (req, res, next) => {
  if (req.params.id) {
    return res.status(200).json({
      error: false,
      message: `Here you can get an order`,
    });
  } else {
    return res.status(400).json({
      error: true,
      message: `You've to provide a order`,
    });
  }
};

module.exports = { getAllOrders, getSingleOrder };
