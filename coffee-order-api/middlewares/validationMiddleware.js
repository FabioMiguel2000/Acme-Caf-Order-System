const crypto = require("crypto");
const User = require("../models/user"); // Assuming you have a User model
const { returnResponse } = require("../services/responseHandler"); // Assuming a response handler

const validateRequest = async (req, res, next) => {
  const signature = req.headers["x-signature"];
  const timestamp = req.headers["x-timestamp"];
  const maxTimestampTolerance = 10 * 1000;

  console.log(signature);

  if (!signature) {
    return returnResponse(res, 400, false, `Unauthorized`);
  }

  const client = req.body.client || req.query.client;
  if (!client) {
    return returnResponse(res, 400, false, "Missing user ID");
  }

  const user = await User.findById(client).select("publicKey");
  if (!user) {
    return returnResponse(res, 401, false, `Unauthorized user not found`);
  }

  const publicKeyPem = user.publicKey;

  let dataToVerify = constructDataForValidation(
    req.url,
    req.body,
    client,
    timestamp
  );

  const verifier = crypto.createVerify("RSA-SHA256");
  verifier.update(dataToVerify);
  const isVerified = verifier.verify(publicKeyPem, signature, "base64");

  if (!isVerified) {
    return returnResponse(
      res,
      400,
      false,
      `Unauthorized ( Invalid signature )`
    );
  }

  if (!validateTimestamp(timestamp, maxTimestampTolerance)) {
    return returnResponse(
      res,
      400,
      false,
      `Unauthorized ( Possible replay attack )`
    );
  }

  next();
};

const constructDataForValidation = (url, body, client, timestamp) => {
  const path = url.split("?")[0];
  switch (path) {
    case `/orders/create`:
      return `${body}`;
    case `/orders/client`:
    case `/vouchers/client`:
      return `${client}&${timestamp}`;
    default:
      throw new Error(`Unexpected path: ${url}`);
  }
};

const validateTimestamp = (requestTimestamp, maxTimestampTolerance) => {
  const currentTimestamp = Date.now();
  const timestampDifference = Math.abs(currentTimestamp - requestTimestamp);
  console.log(currentTimestamp, requestTimestamp, timestampDifference);

  return timestampDifference <= maxTimestampTolerance;
};

module.exports = validateRequest;
