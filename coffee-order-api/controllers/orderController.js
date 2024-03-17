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

module.exports = { getAllOrders, getOrderByID, getOrderByUser };
