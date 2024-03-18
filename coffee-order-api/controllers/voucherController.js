// const Voucher = require('../models/voucherModel');

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

// const createVoucher = async (userId)=>{

// }

module.exports = { getAllVouchers,  getSingleVoucher};