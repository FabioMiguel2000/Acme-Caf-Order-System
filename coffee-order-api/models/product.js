const mongoose = require('mongoose');

const productCategories = [
    "Oleato",
    "Hot Coffees",
    "Cold Coffees",
    "Cappuccinos",
    "Frapuccinos",
    "Mochas",
    "Promotions"
]

const productSchema = new mongoose.Schema({
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


productSchema.plugin(require('mongoose-autopopulate'));

module.exports = mongoose.model('Product', productSchema);