const express = require("express");
const cors = require("cors");

const userRoutes = require("./routers/userRoutes");
const orderRoutes = require("./routers/orderRoutes");
const productRoutes = require("./routers/productRoutes");
const voucherRoutes = require("./routers/voucherRoutes");
const authRoutes = require("./routers/authRoutes");

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
app.use("/api/auth/", authRoutes);

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
