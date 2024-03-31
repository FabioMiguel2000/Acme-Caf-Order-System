const mongoose = require('mongoose');

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
    type: mongoose.Schema.Types.ObjectId,
    ref:"Category",
    autopopulate: true,
  }
});

productSchema.plugin(require('mongoose-autopopulate'));

module.exports = mongoose.model('Product', productSchema);