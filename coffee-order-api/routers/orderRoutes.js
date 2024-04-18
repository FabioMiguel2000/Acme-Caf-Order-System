const express = require("express");
const router = express.Router();
const orderController = require("../controllers/orderController");
const validateRequest = require("../middlewares/validationMiddleware");

router.get("/orders", orderController.getAllOrders);
router.get("/orders/client", validateRequest, orderController.getOrderByUser);
router.post("/orders/create", validateRequest, orderController.createOrder);
router.post("/orders/validation/:id", orderController.validateOrder);
router.get("/orders/:id", orderController.getOrderByID);

module.exports = router;
