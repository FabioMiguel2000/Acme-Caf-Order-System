const express = require("express")
const router = express.Router();
const productController = require("../controllers/productController");

router.get("/productCategories", productController.getProductCategories);
router.get("/products", productController.getAllProducts);
router.get("/products/:id", productController.getProductById);


module.exports = router;