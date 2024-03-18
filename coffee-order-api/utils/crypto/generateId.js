const crypto = require("crypto");

const BYTES_SIZE = 16; // 16 bytes = 32 hexadecimals

function generateUUID() {
  return crypto.randomBytes(BYTES_SIZE).toString("hex");
}

module.exports = generateUUID;