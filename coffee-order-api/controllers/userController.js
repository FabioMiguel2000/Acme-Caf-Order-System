const User = require("../models/user");
const { createVoucher } = require("./voucherController");


const updateUserAccumulatedExpenses = async (userId, expenses) => {
    try {
        const user = await User.findById(userId);
        user.accumulatedExpenses += expenses;
        
        for (let i = 0; i < Math.floor(expenses / 100); i++) {
            await createVoucher(user, "Discount");
            user.accumulatedExpenses -= 100;
        }

        await user.save();
    } catch (error) {
        throw new Error("Failed to update user accumulated expenses");
    }
}

const updateUserAccumulatedCoffeeBuys = async (userId, cupsNum) => {
    try {
        const user = await User.findById(userId);
        user.accumulatedCoffeeBuys += cupsNum;

        for (let i = 0; i < Math.floor(cupsNum / 3); i++) {
            await createVoucher(user, "FreeCoffee");
            user.accumulatedCoffeeBuys -= 3;
        }
        await user.save();
    } catch (error) {
        throw new Error("Failed to update user accumulated coffee buys");
    }
}


const getAllUsers = async (req, res) => {
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

const getSingleUser = async (req, res) => {
    try {   
        const { id } = req.params;
        const user = await User.findById(id).select('-password -publicKey');

        if (!user) {
            return res.status(404).json({
                error: true,
                success: false,
                message: `User with id: ${id} not found`
            });
        }
        return res.status(200).json({
            success: true,
            message: `Retrieved user with id: ${id}`,
            data: user
        });
    } catch (error) {
        return res.status(500).json({
            error: true,
            success: false,
            message: "Failed to retrieve user"
        });
    }
}

module.exports = { getAllUsers, getSingleUser, updateUserAccumulatedExpenses, updateUserAccumulatedCoffeeBuys};