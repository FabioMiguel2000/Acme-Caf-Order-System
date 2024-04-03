const User = require("../models/user");
const { returnResponse } = require("../services/general");
const { createVoucher } = require("./voucherController");


const updateUserAccumulatedExpenses = async (userId, expenses) => {
    try {
        const user = await User.findById(userId);

        let accumulatedExpenses = user.accumulatedExpenses + expenses;
        const num = Math.floor(accumulatedExpenses / 100);

        for (let i = 0; i < num; i++) {
            await createVoucher(user, "Discount");
            accumulatedExpenses -= 100;
        }
        user.accumulatedExpenses = accumulatedExpenses;
        await user.save();
    } catch (error) {
        throw new Error("Failed to update user accumulated expenses");
    }
}

const updateUserAccumulatedCoffeeBuys = async (userId, cupsNum) => {
    try {
        const user = await User.findById(userId);

        let accumulatedCoffeeBuys = user.accumulatedCoffeeBuys + cupsNum;
        const num = Math.floor(accumulatedCoffeeBuys / 3);

        for (let i = 0; i < num; i++) {
            await createVoucher(user, "FreeCoffee");
            accumulatedCoffeeBuys -= 3;
        }
        user.accumulatedCoffeeBuys = accumulatedCoffeeBuys;
        await user.save();

    } catch (error) {
        throw new Error("Failed to update user accumulated coffee buys");
    }
}


const getAllUsers = async (req, res) => {
    try {   
        const users = await User.find().select('-password -publicKey');
        returnResponse(res, 200, true, `Retrieved ${users.length} users`, users);
    } catch (error) {
        returnResponse(res, 500, false, `Failed to retrieve users`);
    }
}

const getSingleUser = async (req, res) => {
    try {   
        const { id } = req.params;
        const user = await User.findById(id).select('-password -publicKey');
        if (!user) {
            returnResponse (res, 404, false, `User with id: ${id} not found`);
        }
        returnResponse(res, 200, true, `Retrieved user with id: ${id}`, user);
    } catch (error) {
        returnResponse(res, 500, false, `Failed to retrieve user`);
    }
}

module.exports = { getAllUsers, getSingleUser, updateUserAccumulatedExpenses, updateUserAccumulatedCoffeeBuys};