const User = require("../models/user");
const bcrypt = require("bcryptjs");
const { encryptPassword } = require("../utils/crypto/bcryptPassword");
const { returnResponse } = require("../services/general");

const loginUser = async (req, res) => {
  try {
    if(req.body.email && req.body.password){
      const userInput = ({
        email: req.body.email,
        password: req.body.password,
        publicKey: req.body.publicKey,
      } = req.body);
  
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

    } else {
      return res.status(500).json({
        success: false,
        message: "Bad request"
      })
    }
    
  } catch (error) {
    return res.status(500).json({
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
      nif: req.body.nif,
      publicKey: req.body.publicKey,
    } = req.body);

    const usernameExists = await User.findOne({
      email: userInput.email,
    });

    if (usernameExists) {
      returnResponse(res, 409, false, `User already exists ${userInput.email}`);
    }

    const nifExists = await User.findOne({
      nif: userInput.nif,
    });

    if (nifExists) {
      returnResponse(res, 409, false, `NIF already registered ${userInput.nif}`);
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
      returnResponse(res, 201, true, `User created ${filteredUser}`, filteredUser);
  } catch (error) {
    return returnResponse(res, 500, false, `Failed to register user ${error}`);
  }
};

module.exports = { registerUser, loginUser };
