const express = require('express')
const app = express();
const port = process.env.PORT || 3000;
let userRoutes = require("./routers/userRoutes");
let orderRoutes = require("./routers/orderRoutes");
let productRoutes = require("./routers/productRoutes");
let voucherRoutes = require("./routers/voucherRoutes")
let receiptRoutes = require("./routers/receiptRoutes");

app.use("/", userRoutes);
app.use("/", orderRoutes);
app.use("/", productRoutes);
app.use("/", voucherRoutes);
app.use("/", receiptRoutes);


app.get('/', (req, res) => {
    res.send('Welcome to coffe-order-api');
});


app.listen(port, () => {
    console.log(`Server is starting ${port}`);
})

