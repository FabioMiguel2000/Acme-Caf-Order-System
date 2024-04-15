const express = require("express")
const router = express.Router();
const voucherController = require("../controllers/voucherController");

router.get("/vouchers", voucherController.getAllVouchers);
router.get("/vouchers/client", voucherController.getVoucherByUser);
router.get("/vouchers/:id", voucherController.getVoucherById);
router.post("/vouchers/create", voucherController.createDirectVoucher);

module.exports = router;