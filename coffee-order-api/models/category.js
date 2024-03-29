const { default: mongoose } = require("mongoose");

const categorySchema = new mongoose.Schema({
    name: {
        type: String,
        required: true,
    },
    img: {
        type: String,
        required: true,
    }
});

module.exports = mongoose('Category', categorySchema);