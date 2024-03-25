const fs = require("fs");
const path = require("path");

const dbconnection = require("../services/dbconnection");
const { encryptPasswords } = require("./crypto/bcryptPassword");

// Modularize file reading
const readJSONFile = (fileName) =>
  JSON.parse(fs.readFileSync(path.join(__dirname, `./seeders/${fileName}.json`), "utf-8"));

const productSeeders = readJSONFile("productSeeders");
const userSeeders = readJSONFile("userSeeders");
const orderSeeders = readJSONFile("orderSeeders");

const Product = require("../models/product");
const User = require("../models/user");
const Order = require("../models/order");
const Voucher = require("../models/voucher");

const { createOrderByProductNames } = require("../controllers/orderController");

const importData = async () => {
  try {
    await dbconnection();
    const userSeedersEncrypted = await encryptPasswords(userSeeders);

    await Promise.all([Product.deleteMany(), User.deleteMany(), Order.deleteMany(), Voucher.deleteMany()]);
    await Promise.all([Product.insertMany(productSeeders), User.insertMany(userSeedersEncrypted)]);

    for (let order of orderSeeders) {
      await createOrderByProductNames(order)
    }

    console.log("Data has been seeded!");
    gracefulExit();
  } catch (error) {
    console.error("Failed to seed MongoDB:", error);
    gracefulExit(1);
  }
};

const destroyData = async () => {
  try {
    await dbconnection();
    await Promise.all([Product.deleteMany(), User.deleteMany(), Order.deleteMany(), Voucher.deleteMany()]);

    console.log("Data has been destroyed!");
    gracefulExit();
  } catch (error) {
    console.error("Failed to destroy MongoDB:", error);
    gracefulExit(1);
  }
};

const gracefulExit = (code = 0) => process.exit(code);

// Process command line arguments
if (process.argv[2] === "-d") {
  destroyData();
} else {
  importData();
}