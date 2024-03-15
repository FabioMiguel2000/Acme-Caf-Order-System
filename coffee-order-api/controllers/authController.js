const User = require("../models/user");
const encryptPasswords = require('../utils/crypto/bcryptPassword');

const loginUser = async (req, res) => {
    try {   


        const users = await User.find().select('-password -publicKey');
        return res.status(200).json({
            success: true,
            message: `Retrieved ${users.length} users`,
            data: users
        });
    } catch (error) {
        return res.status(500).json({
            error: true,
            success: false,
            message: "Failed to retrieve users"
        });
    }
}

const registerUser = async (req, res) => {
    try {   
        const userInput = {
            name: req.body.name,
            password: req.body.password,
            confirmPassword: req.body.confirmPassword,
            nif: req.body.nif,
            publicKey: req.body.publicKey,
        } = req.body

        console.log(userInput)

        const usernameExists = await User.findOne({
            nif: userInput.nif
        })
        if(usernameExists){
            return res.status(409).json({ success: false, message: `User already exists ${user}`});
        }

        const newUser = new User({
            name: userInput.name,
            password: await encryptPasswords(userInput.password),
            nif: userInput.nif,
            publicKey: userInput.publicKey,
        }).select('-password -publicKey');

        await newUser.save();

        return res.status(201).json({ success: true, message: `User created ${newUser}`, data: newUser});

    } catch (error) {
        return res.status(500).json({
            error: true,
            success: false,
            message: "Failed to register user: " + error
        });
    }
}

module.exports = { registerUser, loginUser};