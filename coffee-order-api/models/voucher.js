const mongoose = require('mongoose');

export const voucherSchema = new mongoose.Schema({
    uuid: {
      type: String,
      required: true,
      unique: true,
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
    client: { type: Schema.Types.ObjectId, ref: "User" },
    used: {
      type: Boolean,
      default: false,
    },
});

voucherSchema.plugin(require('mongoose-autopopulate'));

module.exports = mongoose.model('Voucher', voucherSchema);
