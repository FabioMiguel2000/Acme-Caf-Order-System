const { default: mongoose } = require("mongoose");

const categorySchema = new mongoose.Schema({
    name: {
        type: String,
        required: true,
    },
    //product name with no space to use in relation with products
    work_name: {
        type: String,
        required: true
    },
    img: {
        type: String,
        required: true,
    }
});

module.exports = mongoose('Category', categorySchema);