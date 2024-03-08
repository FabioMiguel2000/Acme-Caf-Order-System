const { MongoClient } = require("mongodb");
const url = "mongodb://mongo:27017/acme-coffee";

let db = null;

async function dbconnection() {
  if (db) return db; // Reuse existing connection if already connected
  try {
    const client = await MongoClient.connect(url, { useNewUrlParser: true, useUnifiedTopology: true });
    db = client.db();
    console.log("Database connected!");
    return db;
  } catch (err) {
    console.log("Error in connecting to database", err);
    throw err;
  }
}

module.exports = dbconnection;