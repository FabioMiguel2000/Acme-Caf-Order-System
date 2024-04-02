const Product = require("../models/product");

const getAllProducts = async (req, res) => {
  try {
    const products = await Product.find();
    return res.status(200).json({
      success: true,
      message: `Retrieved ${products.length} products`,
      data: products,
    });
  } catch (error) {
    return res.status(500).json({
      error: true,
      success: false,
      message: "Failed to retrieve products",
    });
  }
};

const getProductById = async (req, res) => {
  try {
    const { id } = req.params;
    const product = await Product.findById(id);
    if (!product) {
      return res.status(404).json({
        error: true,
        success: false,
        message: `Product with id: ${id} not found`,
      });
    }
    return res.status(200).json({
      success: true,
      message: `Retrieved product with id: ${id}`,
      data: product,
    });
  } catch (error) {
    return res.status(500).json({
      error: true,
      success: false,
      message: "Failed to retrieve product",
    });
  }
};

const getProductByCategory = async (req, res) => {
  try {
    const { category } = req.query;
    if (category === undefined || category === "") {
      return res.status(400).json({
        error: true,
        success: false,
        message:
          "Bad Request: category query parameter is required and cannot be empty, e.g. /products/category?category=Hot%20Coffees",
      });
    }
    const products = await Product.find({ productCategory: category });
    if (products.length === 0) {
      return res.status(404).json({
        error: true,
        success: false,
        message: `Products with category: ${category} not found`,
      });
    }
    return res.status(200).json({
      success: true,
      message: `Retrieved ${products.length} products with category: ${category}`,
      data: products,
    });
  } catch (error) {
    return res.status(500).json({
      error: true,
      success: false,
      message: "Failed to retrieve products by category",
    });
  }
};

module.exports = { getAllProducts, getProductById, getProductByCategory };
