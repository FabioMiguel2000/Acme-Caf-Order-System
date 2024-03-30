const ProductCategory = require('../models/category');
const Product = require('../models/product');
const { returnResponse } = require('../services/general');


const getProductCategories = async (req, res) => {
    try {
        const productCategories = await ProductCategory.find();
        //in this way becouse i want only a list to be returned and not object with my list inside
        res.json(productCategories)
    } catch (error) {
        returnResponse(res, 404, false, "error " + error.message);
    }
}

const createProduct = async (product) => {
    try {
        const category = await ProductCategory.findOne({ _name: product.category });

        if (!category) {
            throw new Error(`Category with name ${product.category} not found`);
        }
        const newProduct = await new Product({
            name: product.name,
            price: product.price,
            imgURL: product.imgURL,
            category: category._id
        }).save();

    } catch (error) {
        throw new Error(`Failed to create product ${error}`);
    }
}

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
            message: `Failed to retrieve products ${error}`
        });
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


module.exports = { getAllProducts, getProductById, getProductCategories, createProduct };