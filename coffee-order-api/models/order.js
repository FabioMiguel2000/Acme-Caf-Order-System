const mongoose = require('mongoose');
const generateUUID = require('../utils/crypto/generateId');

const orderSchema = new mongoose.Schema({
  _id: {
    type: String,
    default: generateUUID,
  },
  name: {
    type: String,
    required: true,
  },
  price: {
    type: Number,
    required: true,
  },
  imgURL: {
    type: String,
    required: true,
  },
  productCategory: {
    type: String,
    enum: productCategories,
  },
  products: [
    {
      product: { type: Schema.Types.ObjectId, ref: "Product", autopopulate: true },
      quantity: { type: Number, default: 1 },
    },
  ],
  client: { type: Schema.Types.ObjectId, ref: "User", autopopulate: true },
  status: {
    type: String,
    enum: ["Verified", "Pending"],
    default: "Pending",
  },
  discountVoucher: {
    type: Schema.Types.ObjectId, ref: "DiscountVoucher", default: null, autopopulate: true
  },
  freeCoffeeVoucher: {
    type: Schema.Types.ObjectId, ref: "FreeCoffeeVoucher", default: null, autopopulate: true
  },
});

orderSchema.plugin(require('mongoose-autopopulate'));

module.exports = mongoose.model('Order', orderSchema);