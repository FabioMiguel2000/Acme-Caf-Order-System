const express = require("express")
const router = express.Router();
const voucherController = require("../controllers/voucherController");

router.get("/getAllVouchers", voucherController.getAllVouchers);
router.get("/getSingleVoucher/:id", voucherController.getSingleVoucher);

module.exports = router;