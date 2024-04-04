/**
 * Return response for HTTP request.
 * @param {Object} res - The response object.
 * @param {number} [code=404] - The HTTP status code.
 * @param {boolean} [successStatus=false] - Indicates if the request was successful.
 * @param {string} [message="Something went wrong"] - The response message.
 * @param {Object|null} [data=null] - The data to be sent in the response.
 */
function returnResponse(
  res,
  code = 404,
  successStatus = false,
  message = "Something went wrong",
  data = null
) {
  return res.status(code).json({
    success: successStatus,
    message,
    data,
  });
}

module.exports = { returnResponse };
