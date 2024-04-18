const express = require("express");
const router = express.Router();
const voucherController = require("../controllers/voucherController");
const validateRequest = require("../middlewares/validationMiddleware");

router.get("/vouchers", voucherController.getAllVouchers);
router.get(
  "/vouchers/client",
  validateRequest,
  voucherController.getVoucherByUser
);
router.get("/vouchers/:id", voucherController.getVoucherById);

module.exports = router;
