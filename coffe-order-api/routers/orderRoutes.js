const express = require("express")
const router = express.Router();
const orderController = require("../controllers/orderController");

router.get("/getAllOrders", orderController.getAllOrders);
router.get("/getSingleOrder/:id", orderController.getSingleOrder);

module.exports = router;