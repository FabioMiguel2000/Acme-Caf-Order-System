const mongoClient = require("mongodb").MongoClient;
var url = "mongodb+srv://coffe-order-admin:VT71aM9o4MI8gorK@coffe-order-server.q8zaut2.mongodb.net/?retryWrites=true&w=majority&appName=coffe-order-server";

const dbconnection = mongoClient.connect(url, function(err, db){
    if(err){
        throw err
    } else {
        console.log("Database connected");
    }
});

module.exports = dbconnection;