const express = require("express");
const cors = require("cors");

let userRoutes = require("./routers/userRoutes");
let orderRoutes = require("./routers/orderRoutes");
let productRoutes = require("./routers/productRoutes");
let voucherRoutes = require("./routers/voucherRoutes");
const dbconnection = require("./services/dbconnection");

const app = express();
const port = process.env.PORT || 3000;

app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use("/api/", userRoutes);
app.use("/api/", orderRoutes);
app.use("/api/", productRoutes);
app.use("/api/", voucherRoutes);

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
