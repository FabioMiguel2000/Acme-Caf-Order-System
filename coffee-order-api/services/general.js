/**
 * return response for http request
 * @param res result response
 * @param code response code
 * @param sucessStatus if sucess or not 
 * @param message response description
 */

function returnResponse(res, code, sucessStatus, message, data) {
    if (!code){
        code = 404
    }

    if(!sucessStatus){
        sucessStatus = false
    }

    if(!message){
        message = "Something went wrong";
    }

    if(!data){
        data = null;
    }
    return res.status(code).json({
        success: sucessStatus,
        message: message,
        data: data
    });
}

module.exports = { returnResponse };