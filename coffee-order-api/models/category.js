const { default: mongoose } = require("mongoose");

const categorySchema = new mongoose.Schema({
    name: {
        type: String,
        required: true,
    },
    _name: {
        type: String,
        required: true,
    },
    size: {
        type: Number,
        required: true,
    }, 
    img: {
        type: String,
        required: true,
    }
});

categorySchema.plugin(require('mongoose-autopopulate'));

module.exports = mongoose.model('Category', categorySchema);