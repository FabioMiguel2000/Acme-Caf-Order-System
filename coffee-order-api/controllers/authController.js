const User = require("../models/user");
const bcrypt = require("bcryptjs");
const { encryptPassword } = require("../utils/crypto/bcryptPassword");
const general = require("../services/general")

const validEmailFormat = (email) => {
  const emailRegex =
    /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return emailRegex.test(email);
};

const loginUser = async (req, res) => {
  //try {
    const userInput = ({
      email: req.body.email,
      password: req.body.password,
      publicKey: req.body.publicKey,
    } = req.body);
    if (!validEmailFormat(userInput.email)) {
      general.returnResponse(res, 409, false, "Invalid email format")
    }
    let user = await User.findOne({
      email: userInput.email,
    }).then(result => {
      if(result){
        let passwordIsCorrect = bcrypt.compareSync(userInput.password, result.password)//to avoid asycn one
        if(passwordIsCorrect){
          general.returnResponse(res, 200, true, "User authenticated")
        }else {
          general.returnResponse(res, 409, false, "Email or password are incorrect")
        }
      } else {
        general.returnResponse(res, 409, false, "User not found")
      }
    }).catch((error) => {
      general.returnResponse(res, 409, false, "Something when wrong")
    });
    /*if (!user) {
      console.log("AQUI 2");
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
      console.log("AQUI 3");
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
    });*/
  /*} catch (error) {
    return res.status(500).json({
      error: true,
      success: false,
      message: `Failed to retrieve users ${error}`,
    });
  }*/
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
