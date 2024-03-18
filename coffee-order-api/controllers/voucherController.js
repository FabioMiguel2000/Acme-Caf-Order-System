const Voucher = require('../models/voucher');

const getAllVouchers = (req, res, next) => {
    return res.status(200).json({
        error: false,
        data: `Here you can get all vouchers`
    });
}

const getSingleVoucher = (req, res, next) => {
    if(req.params.id){
        return res.status(200).json({
            error: false,
            message: `Here you can get a voucher`
        });
    } else {
        return res.status(400).json({
            error: true,
            message:`You've to provide a voucher`
        });
    }
}

const createVoucher = async (user, voucherType)=>{
    try{
        if (voucherType !== "Discount" && voucherType !== "FreeCoffee"){
            throw new Error("Invalid voucher type");
        }
        const voucher = new Voucher({
            user,
            type: voucherType
        });
        await voucher.save();
    }catch(error){
        throw new Error("Failed to create voucher");
    }
}

module.exports = { getAllVouchers,  getSingleVoucher, createVoucher};