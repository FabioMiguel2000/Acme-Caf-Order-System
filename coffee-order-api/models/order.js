const mongoose = require('mongoose');

const orderSchema = new mongoose.Schema({
  uuid: {
    type: String,
    required: true,
    unique: true,
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