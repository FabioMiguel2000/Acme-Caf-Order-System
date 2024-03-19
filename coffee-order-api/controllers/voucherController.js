const Voucher = require('../models/voucher');

const getAllVouchers = async (req, res) => {
    try {
        const vouchers = await Voucher.find();
        return res.status(200).json({
            success: true,
            message: `Retrieved ${vouchers.length} vouchers`,
            data: vouchers
        });
    } catch (error) {
        return res.status(500).json({
            error: true,
            success: false,
            message: "Failed to retrieve vouchers"
        });
    }
}

const getVoucherById = async (req, res) => {
    try {
        const { id } = req.params;
        const voucher = await Voucher.findById(id);
        if (!voucher) {
            return res.status(404).json({
                error: true,
                success: false,
                message: `Voucher with id: ${id} not found`
            });
        }
        return res.status(200).json({
            success: true,
            message: `Retrieved voucher with id: ${id}`,
            data: voucher
        });
    } catch (error) {
        return res.status(500).json({
            error: true,
            success: false,
            message: "Failed to retrieve voucher"
        });
    }
}

const getVoucherByUser = async (req, res) => {
    try {
        const { client } = req.query;
        const vouchers = await Voucher.find({ client });
        return res.status(200).json({
            success: true,
            message: `Retrieved ${vouchers.length} vouchers from client: ${client}`,
            data: vouchers
        });
    } catch (error) {
        return res.status(500).json({
            error: true,
            success: false,
            message: "Failed to retrieve vouchers",
        });
    }
}

const createVoucher = async (user, voucherType)=>{
    try{
        if (voucherType !== "Discount" && voucherType !== "FreeCoffee"){
            throw new Error("Invalid voucher type");
        }
        const voucher = new Voucher({
            client: user._id,
            type: voucherType
        });
        await voucher.save();
    }catch(error){
        throw new Error("Failed to create voucher");
    }
}

module.exports = { getAllVouchers,  getVoucherById, createVoucher, getVoucherByUser};