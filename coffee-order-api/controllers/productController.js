const Product = require('../models/product');
const ProductCategory = require('../models/category');
const { returnResponse } = require('../services/general');

const getAllProducts = async (req, res) => {
    try {
        const products = await Product.find();
        return res.status(200).json({
            success: true,
            message: `Retrieved ${products.length} products`,
            data: products
        });
    } catch (error) {
        return res.status(500).json({
            error: true,
            success: false,
            message: "Failed to retrieve products"
        });
    }
}

const getProductCategories = async (req, res) => {
    try {
        const productCategories = await ProductCategory.find();
        res.json(productCategories)
    } catch(error){
        returnResponse(res, 404, false, "error "+error.message);
    }
}

const getProductById = async (req, res) => {
    try {
        const { id } = req.params;
        const product = await Product.findById(id);
        if (!product) {
            return res.status(404).json({
                error: true,
                success: false,
                message: `Product with id: ${id} not found`
            });
        }
        return res.status(200).json({
            success: true,
            message: `Retrieved product with id: ${id}`,
            data: product
        });
    } catch (error) {
        returnResponse(res, 404, false, error.message);
    }
}


module.exports = { getAllProducts,  getProductById, getProductCategories};