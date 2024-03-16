const fs = require("fs");
const path = require("path");
const mongoose = require("mongoose");

const dbconnection = require("../services/dbconnection");
const { encryptPasswords } = require("./crypto/bcryptPassword");

const productSeeders = JSON.parse(
  fs.readFileSync(
    path.join(__dirname, "./seeders/productSeeders.json"),
    "utf-8"
  )
);
const userSeeders = JSON.parse(
  fs.readFileSync(path.join(__dirname, "./seeders/userSeeders.json"), "utf-8")
);

const orderSeeders = JSON.parse(
  fs.readFileSync(path.join(__dirname, "./seeders/orderSeeders.json"), "utf-8")
);

const Product = require("../models/product");
const User = require("../models/user");
const Order = require("../models/order");

const createOrderByNifAndProductNames = async (order) => {
  const user = await User.findOne({ nif: order.client });
  let products = [];

  for (let i = 0; i < order.products.length; i++) {
    let product = await Product.findOne({ name: order.products[i].product_name });
    products.push({_id: product, quantity: order.products[i].quantity});
  }
  const orderObj = new Order({
    client: user._id,
    products,
  });

  await orderObj.save();

}

const importData = async () => {
  try {
    await dbconnection();

    const userSeedersEncrypted = await encryptPasswords(userSeeders);

    await Product.deleteMany();
    await User.deleteMany();
    await Order.deleteMany();

    await Product.insertMany(productSeeders);
    await User.insertMany(userSeedersEncrypted);

    for (const orderData of orderSeeders) {
      await createOrderByNifAndProductNames(orderData);
    }

    console.log("Data has been seeded!");

    process.exit();
  } catch (error) {
    console.log("Failed to seed MongoDB:", error);
    process.exit(1);
  }
};

const destroyData = async () => {
  try {
    await dbconnection();

    await Product.deleteMany();
    await User.deleteMany();

    console.log("Data has been destroyed!");

    process.exit();
  } catch (error) {
    console.log("Failed to destroy MongoDB:", error);
    process.exit(1);
  }
};

if (process.argv[2] == "-d") {
  destroyData();
} else {
  importData();
}
