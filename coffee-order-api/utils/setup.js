const fs = require("fs");
const path = require("path");

const dbconnection = require("../services/dbconnection");
const { encryptPasswords } = require("./crypto/bcryptPassword");

// Modularize file reading
const readJSONFile = (fileName) =>
  JSON.parse(fs.readFileSync(path.join(__dirname, `./seeders/${fileName}.json`), "utf-8"));

const readImageAsBase64 = (imageName) => {
  const imagePath = path.join(__dirname, `./seeders/images/${imageName}`);
  if (fs.existsSync(imagePath)) {
    return fs.readFileSync(imagePath, { encoding: 'base64' });
  }
  console.warn(`Image ${imageName} not found.`);
  return null;
};

const productCategorySeeders = readJSONFile("categorySeeders").map(category => {
  console.log(category.img)
  return {
    ...category,
    img: readImageAsBase64(category.img) || '' // Fallback to empty string if image not found
  };
});

console.log(productCategorySeeders)

// const productCategorySeeders = readJSONFile("productCategorySeeders");
const productSeeders = readJSONFile("productSeeders");
const userSeeders = readJSONFile("userSeeders");
const orderSeeders = readJSONFile("orderSeeders");


const Product = require("../models/product");
const User = require("../models/user");
const Order = require("../models/order");
const Voucher = require("../models/voucher");
const Category = require("../models/category");

const { createOrderByProductNames } = require("../controllers/orderController");
const { createProduct } = require("../controllers/productController");


const importData = async () => {
  try {
    await dbconnection();
    const userSeedersEncrypted = await encryptPasswords(userSeeders);
    await Promise.all([Product.deleteMany(), User.deleteMany(), Order.deleteMany(), Voucher.deleteMany()]);
    await Promise.all([Category.insertMany(productCategorySeeders), User.insertMany(userSeedersEncrypted)]);

    for (let product of productSeeders) {
      await createProduct(product);
    }

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
    await Promise.all([Category.deleteMany(), Product.deleteMany(), User.deleteMany(), Order.deleteMany(), Voucher.deleteMany()]);

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