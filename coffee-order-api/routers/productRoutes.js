const express = require("express")
const router = express.Router();
const productController = require("../controllers/productController");

router.get("/products", productController.getAllProducts);
router.get("/products/:id", productController.getProductById);
router.get("/productCategories", productController.getProductCategories);

module.exports = router;