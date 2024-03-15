const User = require("../models/user");
const bcrypt = require("bcryptjs");
const { encryptPassword } = require("../utils/crypto/bcryptPassword");

const loginUser = async (req, res) => {
  try {
    const userInput = ({
      nif: req.body.nif,
      password: req.body.password,
      publicKey: req.body.publicKey,
    } = req.body);

    let user = await User.findOne({
      nif: userInput.nif,
    });
    if (!user) {
      return res.status(409).json({
        success: false,
        message: `Authentication Failed: The username that you've entered doesn't match any account.`,
      });
    }

    const isPasswordCorrect = await bcrypt.compare(
      userInput.password,
      user.password
    );

    if (!isPasswordCorrect) {
      return res.status(409).json({
        success: false,
        message: `Authentication Failed: Invalid logid name or password.`,
      });
    }

    user = await User.findOneAndUpdate(
      { nif: userInput.nif },
      { $set: { publicKey: userInput.publicKey } },
      {
        new: true,
        runValidators: true,
      }
    ).select("-password -publicKey");

    return res.status(200).json({
      success: true,
      message: `User authenticated ${user}`,
      data: user,
    });
  } catch (error) {
    return res.status(500).json({
      error: true,
      success: false,
      message: "Failed to retrieve users",
    });
  }
};

const registerUser = async (req, res) => {
  try {
    const userInput = ({
      name: req.body.name,
      password: req.body.password,
      confirmPassword: req.body.confirmPassword,
      nif: req.body.nif,
      publicKey: req.body.publicKey,
    } = req.body);

    console.log(userInput);

    const usernameExists = await User.findOne({
      nif: userInput.nif,
    });
    if (usernameExists) {
      return res
        .status(409)
        .json({ success: false, message: `User already exists ${user}` });
    }

    const encryptedPassword = await encryptPassword(userInput.password);

    const newUser = new User({
      name: userInput.name,
      password: encryptedPassword,
      nif: userInput.nif,
      publicKey: userInput.publicKey,
    });

    await newUser.save();

    const filteredUser = await User.findById(newUser._id).select(
      "-password -publicKey"
    );

    return res.status(201).json({
      success: true,
      message: `User created ${filteredUser}`,
      data: filteredUser,
    });
  } catch (error) {
    return res.status(500).json({
      error: true,
      success: false,
      message: "Failed to register user: " + error,
    });
  }
};

module.exports = { registerUser, loginUser };
