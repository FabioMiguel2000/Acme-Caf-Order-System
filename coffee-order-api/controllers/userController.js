const User = require("../models/user");

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
            message: "Failed to retrieve users"
        });
    }
}

module.exports = { getAllUsers, getSingleUser};