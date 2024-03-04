const getAllReceipts = (req, res, next) => {
    return res.status(200).json({
        error: false,
        data: `Here you can get all receipts`
    });
}

const getSingleReceipt = (req, res, next) => {
    if(req.params.id){
        return res.status(200).json({
            error: false,
            message: `Here you can get a receipt`
        });
    } else {
        return res.status(400).json({
            error: true,
            message:`You've to provide a receipt`
        });
    }
}

module.exports = { getAllReceipts,  getSingleReceipt};