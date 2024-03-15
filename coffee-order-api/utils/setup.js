const fs = require('fs');
const path = require('path');
const mongoose = require('mongoose');

const dbconnection = require("../services/dbconnection");

const productSeeders = JSON.parse(fs.readFileSync(path.join(__dirname, './seeders/productSeeders.json'), 'utf-8'));
const userSeeders = JSON.parse(fs.readFileSync(path.join(__dirname, './seeders/userSeeders.json'), 'utf-8'));


const seedDB = async () => {
  await dbconnection();

  const Product = require("../models/product");
  const User = require("../models/user");

  await Product.deleteMany();
  await User.deleteMany();

  await Product.insertMany(productSeeders);
  await User.insertMany(userSeeders);

  await mongoose.disconnect();
};

seedDB()
  .then(() => {
    console.log("Seeding Completed!");
    process.exit(0);
  })
  .catch((err) => {
    console.error("Error Seeding", err);
    process.exit(1);
});
