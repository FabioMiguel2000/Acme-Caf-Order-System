const Voucher = require('../models/voucher');
const { returnResponse } = require('../services/responseHandler');

const getAllVouchers = async (req, res) => {
    try {
        const vouchers = await Voucher.find();
        return responseResponse(res, 200, true, `Retrieved ${vouchers.length} vouchers`, vouchers);
    } catch (error) {
        return returnResponse(res, 500, false, `Failed to retrieve vouchers`);
    }
}

const getVoucherById = async (req, res) => {
    try {
        const { id } = req.params;
        const voucher = await Voucher.findById(id);
        if (!voucher) {
            return returnResponse(res, 404, false, `Voucher with id: ${id} not found`);
        }
        return returnResponse(res, 200, true, `Retrieved voucher with id: ${id}`, voucher);
    } catch (error) {
        return returnResponse(res, 500, false, `Failed to retrieve voucher`);
    }
}

const getVoucherByUser = async (req, res) => {
    try {
        const { client } = req.query;
        const vouchers = await Voucher.find({ client });
        return returnResponse(res, 200, true, `Retrieved ${vouchers.length} vouchers from client: ${client}`, vouchers);
    } catch (error) {
        return returnResponse(res, 500, false, `Failed to retrieve vouchers`);
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


const validateVoucher = async (voucherId, userId)=>{
    try{
        if (!voucherId ){
            return null;
        }

        const voucher = await Voucher.findById(voucherId);

        if (!voucher) {
            throw new Error(`Voucher with id: ${voucherId} not found`);
        }
    
        if (voucher.client._id !== userId) {
            throw new Error(`Voucher with id: ${voucherId} does not belong to client with id: ${userId}`);
        }

        if (voucher.used){
            console.log(voucher)

            throw new Error(`Voucher with id ${voucherId} already used`);
        }

        return voucher;
    }catch(error){
        throw new Error(error.message);
    }
}

const useVoucher = async (voucherId)=>{
    try{
        const voucher = await Voucher.findById(voucherId);
        if (!voucher){
            throw new Error("Voucher not found");
        }
        if (voucher.used){
            throw new Error("Voucher already used");
        }
        voucher.used = true;
        await voucher.save();
    }catch(error){
        throw new Error("Failed to use voucher");
    }
}

module.exports = { getAllVouchers,  getVoucherById, createVoucher, getVoucherByUser, useVoucher, validateVoucher};