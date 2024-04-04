const category = require("../models/category");
const ProductCategory = require("../models/category");
const Product = require("../models/product");
const { returnResponse } = require("../services/responseHandler");

const getProductCategories = async (req, res) => {
  try {
    const productCategories = await ProductCategory.find();
    //in this way becouse i want only a list to be returned and not object with my list inside
    // returnResponse(res, 200, true, "Sucess", productCategories);
    return res.status(200).json({
      success: true,
      message: `Retrieved ${productCategories.length} categories`,
      data: productCategories,
    });
  } catch (error) {
    return returnResponse(res, 404, false, "error " + error.message);
  }
};

const createProduct = async (product) => {
  try {
    const category = await ProductCategory.findOne({ _name: product.category });

    if (!category) {
      throw new Error(`Category with name ${product.category} not found`);
    }

    await ProductCategory.findByIdAndUpdate(
      category._id,
      { $inc: { size: 1 } },
      { new: true }
    );

    const newProduct = await new Product({
      name: product.name,
      price: product.price,
      imgURL: product.imgURL,
      category: category._id,
    }).save();
  } catch (error) {
    throw new Error(`Failed to create product ${error}`);
  }
};

const getAllProducts = async (req, res) => {
  try {
    const products = await Product.find();
    return returnResponse(res, 200, true, `Retrieved ${products.length} products`, products);
  } catch (error) {
    return returnResponse(res, 500, false, `Failed to retrieve products ${error}`);
  }
};

const getProductById = async (req, res) => {
  try {
    const { id } = req.params;
    const product = await Product.findById(id);
    if (!product) {
      return returnResponse(res, 404, false, `Product with id: ${id} not found`);
    }
    return returnResponse(res, 200, true, `Retrieved product with id: ${id}`, product);
  } catch (error) {
    return returnResponse(res, 404, false, error.message);
  }
};

const getProductsByCategory = async (req, res) => {
  try {
    const { category } = req.query;
    if (category === undefined || category === "") {
      return returnResponse(res, 400, false, "Bad Request: category query parameter is required and cannot be empty, e.g. /products/category?category=Hot%20Coffees");
    }

    const categoryObjectId = await ProductCategory.findOne({ _name: category })
      .select("_id")
      .lean();

      const products = await Product.aggregate([
        { $match: { category: categoryObjectId._id } },
        {
          $lookup: {
            from: 'categories', 
            localField: 'category', 
            foreignField: '_id', 
            as: 'categoryDetails',
          },
        },
        {
          $unwind: '$categoryDetails',
        },
        {
          $project: {
            name: 1,
            price: 1,
            imgURL: 1,
            category: '$categoryDetails._name',
          },
        },
      ]);

    if (products.length === 0) {
      return returnResponse(res, 404, false, `Products with category: ${category} not found`);
    }
    return returnResponse(res, 200, true, `Retrieved ${products.length} products with category: ${category}`, products);
  } catch (error) {
    return returnResponse(res, 500, false, `Failed to retrieve products by category`);
  }
};

module.exports = {
  getAllProducts,
  getProductById,
  getProductCategories,
  createProduct,
  getProductsByCategory,
};
