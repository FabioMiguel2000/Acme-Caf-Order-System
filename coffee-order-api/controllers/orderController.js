const getAllOrders = (req, res, next) => {
    return res.status(200).json({
        error: false,
        data: `Here you can get all orders`
    });
}

const getSingleOrder = (req, res, next) => {
    if(req.params.id){
        return res.status(200).json({
            error: false,
            message: `Here you can get an order`
        });
    } else {
        return res.status(400).json({
            error: true,
            message:`You've to provide a order`
        });
    }
}

module.exports = { getAllOrders,  getSingleOrder};