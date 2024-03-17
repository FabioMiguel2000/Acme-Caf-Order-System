const User = require("../models/user");
const bcrypt = require("bcryptjs");
const { encryptPassword } = require("../utils/crypto/bcryptPassword");

const validEmailFormat = (email) => {
  const emailRegex =
    /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return emailRegex.test(email);
};

const loginUser = async (req, res) => {
  try {
    const userInput = ({
      email: req.body.email,
      password: req.body.password,
      publicKey: req.body.publicKey,
    } = req.body);

    if (!validEmailFormat(userInput.email)) {
      return res.status(409).json({
        success: false,
        message: "Invalid email format",
      });
    }

    let user = await User.findOne({
      email: userInput.email,
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
      { _id: user._id },
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
      email: req.body.email,
      confirmPassword: req.body.confirmPassword,
      nif: req.body.nif,
      publicKey: req.body.publicKey,
    } = req.body);

    if (!validEmailFormat(userInput.email)) {
      return res.status(409).json({
        success: false,
        message: "Invalid email format",
      });
    }

    if (userInput.password !== userInput.confirmPassword) {
      return res.status(409).json({
        success: false,
        message: "Passwords do not match",
      });
    }

    const usernameExists = await User.findOne({
      email: userInput.email,
    });

    if (usernameExists) {
      return res.status(409).json({
        success: false,
        message: `User already exists ${userInput.email}`,
      });
    }

    const nifExists = await User.findOne({
      nif: userInput.nif,
    });

    if (nifExists) {
      return res.status(409).json({
        success: false,
        message: `NIF already registered ${userInput.nif}`,
      });
    }

    const encryptedPassword = await encryptPassword(userInput.password);

    const newUser = new User({
      name: userInput.name,
      password: encryptedPassword,
      email: userInput.email,
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