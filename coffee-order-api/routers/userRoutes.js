const express = require("express")
const router = express.Router();
const userController = require("../controllers/userController");

router.get("/getAllUsers", userController.getAllUsers);
router.get("/getSingleUser/:id", userController.getSingleUser);

module.exports = router;