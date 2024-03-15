const { connection } = require('mongoose');
const mongoose = require('mongoose');

const url = "mongodb://mongo:27017/acme-coffee";

const options = {
  useUnifiedTopology: true,
  useNewUrlParser: true
}

// dotenv.config({ path: __dirname + "/.env" });

// const DB_URI = process.env.MONGODB_URI;

const dbconnection = async () => {
  try {
      if (connection.readyState) {
          console.log("Using existing connection");
          return;
      }
      await mongoose.connect(url, options);
      console.log("Connected to database");
  } catch (error) {
      console.log(error);
      throw new Error("Error connecting to database");
  }
}


module.exports = dbconnection;
