const express = require("express")
const router = express.Router();
const orderController = require("../controllers/orderController");

router.get("/orders", orderController.getAllOrders);
router.get("/orders/client", orderController.getOrderByUser);
router.get("/orders/:id", orderController.getOrderByID);

module.exports = router;