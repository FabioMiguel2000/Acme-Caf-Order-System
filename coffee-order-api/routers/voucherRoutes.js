const express = require("express")
const router = express.Router();
const voucherController = require("../controllers/voucherController");

router.get("/vouchers", voucherController.getAllVouchers);
router.get("/vouchers/:id", voucherController.getVoucherById);

module.exports = router;