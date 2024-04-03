package com.feup.coffee_order_application.core.crypto

data class PubKey(
    var modulus: ByteArray,
    var exponent: ByteArray
)
