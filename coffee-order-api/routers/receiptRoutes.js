const express = require("express")
const router = express.Router();
const receiptController = require("../controllers/receiptController");

router.get("/getAllReceipts", receiptController.getAllReceipts);
router.get("/getSingleReceipt/:id", receiptController.getSingleReceipt);

module.exports = router;