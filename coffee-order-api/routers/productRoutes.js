const express = require("express")
const router = express.Router();
const productController = require("../controllers/productController");

router.get("/products", productController.getAllProducts);
router.get("/products/:id", productController.getProductById);
router.get("/products/categories", productController.getProductCategories);

module.exports = router;