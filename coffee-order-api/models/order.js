const mongoose = require("mongoose");
const generateUUID = require("../utils/crypto/generateId");
const User = require("./user");
const Product = require("./product");
const Voucher = require("./voucher");

const orderSchema = new mongoose.Schema({
  _id: {
    type: String,
    default: generateUUID,
  },
  client: {
    type: mongoose.Schema.Types.String,
    ref: "User",
    autopopulate: { select: "-password -publicKey" },
  },

  products: [
    {
      product: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Product",
        autopopulate: true,
      },
      quantity: { type: Number, default: 1 },
    },
  ],
  status: {
    type: String,
    enum: ["Verified", "Pending"],
    default: "Pending",
  },
  date: {
    type: Date,
    default: Date.now,
    required: true,
  },
  discountVoucher: {
    type: mongoose.Schema.Types.String,
    ref: "Voucher",
    default: null,
    autopopulate: true,
  },
  freeCoffeeVoucher: {
    type: mongoose.Schema.Types.String,
    ref: "Voucher",
    default: null,
    autopopulate: true,
  },
  subtotal: {
    type: Number,
    required: true,
  },
  promotionDiscount: {
    type: Number,
    required: true,
  },
  total: {
    type: Number,
    required: true,
  },
});

orderSchema.plugin(require("mongoose-autopopulate"));

module.exports = mongoose.model("Order", orderSchema);
