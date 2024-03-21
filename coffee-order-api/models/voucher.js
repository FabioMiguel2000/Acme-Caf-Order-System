const mongoose = require("mongoose");
const generateUUID = require("../utils/crypto/generateId");
const User = require("./user");

const voucherSchema = new mongoose.Schema({
  _id: {
    type: String,
    default: generateUUID,
  },
  type: {
    type: String,
    enum: ["Discount", "FreeCoffee"],
    required: true,
  },
  client: {
    type: mongoose.Schema.Types.String,
    ref: "User",
    autopopulate: { select: "-password -publicKey" },
  },
  used: {
    type: Boolean,
    default: false,
  },
  date: {
    type: Date,
    default: Date.now,
    required: true,
  },
});

voucherSchema.plugin(require("mongoose-autopopulate"));

module.exports = mongoose.model("Voucher", voucherSchema);
