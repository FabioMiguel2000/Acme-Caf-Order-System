const mongoose = require('mongoose');

export const userSchema = new mongoose.Schema({
    uuid: {
      type: String,
      required: true,
      unique: true,
    },
    name: {
      type: String,
      required: true,
    },
    nif: {
        type: String,
        required: true,
    },
    publicKey: {
        type: String,
        required: true,
    },
    accumulatedExpenses:{
        type: Number,
        required: true,
        default: 0,
    },
    accumulatedCoffeeBuys:{
        type: Number,
        required: true,
        default: 0,
    },
});

module.exports = mongoose.model('User', userSchema);
