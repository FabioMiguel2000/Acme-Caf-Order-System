const mongoose = require('mongoose');
const generateUUID = require('../utils/crypto/generateId');

const voucherSchema = new mongoose.Schema({
    _id: {
      type: String,
      default: generateUUID,
    },
    discount: {
      type: Number,
      required: true,
    },
    type: {
      type: String,
      enum: ["Discount", "FreeCoffee"],
      required: true,
    },
    client: { type: mongoose.Schema.Types.ObjectId, ref: "User" },
    used: {
      type: Boolean,
      default: false,
    },
});

voucherSchema.plugin(require('mongoose-autopopulate'));

module.exports = mongoose.model('Voucher', voucherSchema);
