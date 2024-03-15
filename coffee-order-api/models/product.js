const mongoose = require('mongoose');

const productCategories = [
    "Oleato",
    "Hot Coffees",
    "Cold Coffees",
    "Cappuccinos",
    "Frapuccinos",
    "Mochas"
]

export const productSchema = new mongoose.Schema({
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
  }
});

module.exports = mongoose.model('Product', productSchema);