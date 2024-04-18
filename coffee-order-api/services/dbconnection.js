const { connection } = require('mongoose');
const mongoose = require('mongoose');
// require('dotenv').config({ path: __dirname + "/.env" });
require('dotenv').config();

const options = {
  useUnifiedTopology: true,
  useNewUrlParser: true
};

const DB_URI = process.env.MONGODB_URI;

const dbconnection = async () => {
  try {
      if (connection.readyState) {
          console.log("Using existing connection!");
          return;
      }
      await mongoose.connect(DB_URI, options);
      console.log("Connected to database");
  } catch (error) {
      throw new Error("Error connecting to database!");
  }
};

module.exports = dbconnection;
