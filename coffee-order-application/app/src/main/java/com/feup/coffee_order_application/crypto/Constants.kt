package com.feup.coffee_order_application.crypto
object Crypto {
    const val KEY_SIZE = 512
    const val ANDROID_KEYSTORE = "AndroidKeyStore"
    const val KEY_ALGO = "RSA"
    const val SIGN_ALGO = "SHA256WithRSA"
    const val ENC_ALGO = "RSA/NONE/PKCS1Padding"
    const val KeyName = "ACMECAFE"
    const val SerialNr = 1234567890L
}