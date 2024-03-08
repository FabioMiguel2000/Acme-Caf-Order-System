const express = require("express");
const app = express();
const port = process.env.PORT || 3000;
let userRoutes = require("./routers/userRoutes");
let orderRoutes = require("./routers/orderRoutes");
let productRoutes = require("./routers/productRoutes");
let voucherRoutes = require("./routers/voucherRoutes");
let receiptRoutes = require("./routers/receiptRoutes");
const dbconnection = require("./services/dbconnection");

app.use("/", userRoutes);
app.use("/", orderRoutes);
app.use("/", productRoutes);
app.use("/", voucherRoutes);
app.use("/", receiptRoutes);

app.get("/", (req, res) => {
  res.send("Welcome to coffee-order-api");
});

app.listen(port, async () => {
  try {
    await dbconnection(); 
    console.log(`Server is starting on port ${port}`);
  } catch (error) {
    console.error("Failed to connect to the database:", error);
    process.exit(1); 
  }
});
