const express = require("express");
const router = express.Router();
const productController = require("../controllers/productController");

router.get("/productCategories", productController.getProductCategories);
router.get("/products/category", productController.getProductsByCategory);
router.get("/products/:id", productController.getProductById);
router.get("/products", productController.getAllProducts);

module.exports = router;
