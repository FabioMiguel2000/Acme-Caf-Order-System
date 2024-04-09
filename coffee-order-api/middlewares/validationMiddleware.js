const crypto = require("crypto");
const User = require("../models/user"); // Assuming you have a User model
const { returnResponse } = require("../services/responseHandler"); // Assuming a response handler

const MAX_TIMESTAMP_TOLERANCE = 10 * 1000; // 60 seconds tolerance (adjust as needed)

const validateRequest = async (req, res, next) => {
  const signature = req.headers["x-signature"];
  const headerTimestamp = parseInt(req.headers["x-timestamp"], 10);

  console.log(signature);

  if (!signature || !headerTimestamp) {
    return returnResponse(res, 400, false, `Unauthorized`);
  }

  const client = req.body.client || req.query.client;
  if (!client) {
    return returnResponse(res, 400, false, "Missing user ID");
  }

  const user = await User.findById(client).select("publicKey");
  if (!user) {
    return returnResponse(res, 401, false, `Unauthorized`);
  }

  const publicKeyDerHex = user.publicKey;
  const publicKeyDer = Buffer.from(publicKeyDerHex, "hex");
  const publicKeyPem = `-----BEGIN PUBLIC KEY-----\n${publicKeyDer.toString(
    "base64"
  )}\n-----END PUBLIC KEY-----`;

  const signedData = constructDataForValidation(
    req.url,
    req.body,
    headerTimestamp,
    client
  );
  const isValidSignature = crypto.verify(
    "SHA256",
    Buffer.from(signedData, "base64"),
    publicKeyPem,
    Buffer.from(signature, "base64")
  );

  if (!isValidSignature) {
    return returnResponse(res, 401, false, "Unauthorized (Invalid signature)");
  }
  console.log(isValidSignature);

  // Compare timestamps for additional security
  if (!validateTimestamp(headerTimestamp)) {
    return returnResponse(
      res,
      401,
      false,
      "Unauthorized (Possible tampering or replay)"
    );
  }

  // ... Proceed if both signature and timestamps are valid
  next();
};

const constructDataForValidation = (url, body, timestamp, client) => {
  const path = url.split("?")[0];
  console.log(path);
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

const validateTimestamp = (headerTimestamp) => {
  const currentTime = Date.now();
  const timestampDifference = Math.abs(currentTime - headerTimestamp);
  return timestampDifference <= MAX_TIMESTAMP_TOLERANCE;
};

module.exports = validateRequest;
