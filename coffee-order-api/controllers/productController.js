const Product = require('../models/product');

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
        const productCategories = Product.find();
        return res.status(200).json({
            success: true,
            message: `Retrieved ${productCategories.length} product categories`,
            data: productCategories
        });
    } catch(error){
        return res.status(500).json({
            error: true,
            success: false,
            message: "Failed to retrieve product categories"
        }) ;
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
        return res.status(500).json({
            error: true,
            success: false,
            message: "Failed to retrieve product"
        });
    }
}


module.exports = { getAllProducts,  getProductById, getProductCategories};