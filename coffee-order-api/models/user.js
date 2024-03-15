const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
    // uuid: {
    //   type: String,
    //   required: true,
    //   unique: true,
    // },
    password: {
        type: String,
        required: true,
    },
    name: {
      type: String,
      required: true,
    },
    nif: {
        type: String,
        required: true,
        unique: true,
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
