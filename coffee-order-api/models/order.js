const mongoose = require('mongoose');
const generateUUID = require('../utils/crypto/generateId');

const orderSchema = new mongoose.Schema({
  _id: {
    type: String,
    default: generateUUID,
  },
  products: [
    {
      product: { type: mongoose.Schema.Types.ObjectId, ref: "Product", autopopulate: true },
      quantity: { type: Number, default: 1 },
    },
  ],
  client: { type: mongoose.Schema.Types.String, ref: "User", autopopulate: true },
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
});

orderSchema.plugin(require('mongoose-autopopulate'));

module.exports = mongoose.model('Order', orderSchema);