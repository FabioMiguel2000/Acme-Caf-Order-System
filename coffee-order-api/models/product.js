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
  category: {
    type: mongoose.Schema.Types.String,
    ref:"Category",
    autopopulate: true,
  }
});


productSchema.plugin(require('mongoose-autopopulate'));

module.exports = mongoose.model('Product', productSchema);