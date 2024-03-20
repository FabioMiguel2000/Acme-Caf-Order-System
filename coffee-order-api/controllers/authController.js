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

    //TODO: remove it later becouse the android applcation will be responsible for this
    if (userInput.password !== userInput.confirmPassword) {
      return res.status(409).json({
        success: false,
        message: "Passwords do not match",
      });
    }

    await User.find({
      $or: [{email: userInput.email}, {nif: userInput.nif}]
    }).then( result => {
      if(result.length > 0){
        general.returnResponse(res, 409, false, "Email or nif provide already userd in the system")
      } else {
        const encryptedPassword = encryptPassword(userInput.password);
        const newUser = new User({
          name: userInput.name,
          password: encryptedPassword,
          email: userInput.email,
          nif: userInput.nif,
          publicKey: userInput.publicKey,
        });
        newUser.save().then(result => {
          if(result){
            general.returnResponse(res, 201, true,  `User created ${result}`, result)
          } else {
            general.returnResponse(res, 409, true,  `Something went wrong, user not created`)
          }
        }).catch((error) => {
          general.returnResponse(res, 409, true,  `Something went wrong ${error}`)
        })
      }
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
