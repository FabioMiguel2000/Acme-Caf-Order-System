package com.feup.coffee_order_application.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.Calendar
import java.util.GregorianCalendar
import javax.security.auth.x500.X500Principal



class CryptoKeys {
    private var generated = false
    var entry: KeyStore.Entry? = null    // getting a keystore entry (with KeyName) lazily
        get() {
            if (field == null) {
                field = KeyStore.getInstance(Crypto.ANDROID_KEYSTORE).run {
                    load(null)
                    getEntry(Crypto.KeyName, null)
                }
            }
            return field
        }

    fun generateAndStoreKeys(): Boolean {
        try {
            if (!generated) {

                val spec = KeyGenParameterSpec.Builder(
                    Crypto.KeyName,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT or
                            KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
                )
                    .setKeySize(Crypto.KEY_SIZE)
                    .setDigests(KeyProperties.DIGEST_NONE, KeyProperties.DIGEST_SHA256)   // allowed digests for encryption and for signature
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)    // allowed padding schema for encryption
                    .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)      // allowed padding schema for signature
                    .setCertificateSubject(X500Principal("CN=" + Crypto.KeyName))
                    .setCertificateSerialNumber(BigInteger.valueOf(Crypto.SerialNr))
                    .setCertificateNotBefore(GregorianCalendar().time)
                    .setCertificateNotAfter(GregorianCalendar().apply { add(Calendar.YEAR, 10) }.time)
                    .build()
                KeyPairGenerator.getInstance(Crypto.KEY_ALGO, Crypto.ANDROID_KEYSTORE).apply {
                    initialize(spec)
                    generateKeyPair()   // the generated keys are stored in the Android Keystore
                }
            }
        }
        catch (ex: Exception) {
            Log.d("error", "Key generation error: " + ex.message)
            return false
        }
        return true
    }

    private fun getPubKey(): PubKey {
        val pKey = PubKey(ByteArray(0), ByteArray(0))
        try {
            val pub = (entry as KeyStore.PrivateKeyEntry).certificate.publicKey
            pKey.modulus = (pub as RSAPublicKey).modulus.toByteArray()
            pKey.exponent = pub.publicExponent.toByteArray()
        }
        catch (ex: Exception) {
            Log.d("error", "Get public key error: " + ex.message)
        }
        return pKey
    }

    private fun getPrivExp(): ByteArray {
        var exp = ByteArray(0)
        try {
            val priv = (entry as KeyStore.PrivateKeyEntry).privateKey
            exp = (priv as RSAPrivateKey).privateExponent.toByteArray()
        }
        catch (ex: Exception) {
            Log.d("error", "Get private key error: " + ex.message)
        }
        return exp
    }

    fun getPublicKey(): String {
        val pkey = getPubKey()
        val pkeyString = byteArrayToHex(pkey.modulus)
        return pkeyString
    }

    private fun byteArrayToHex(ba: ByteArray): String {
        val sb = StringBuilder(ba.size * 2)
        for (b in ba) sb.append(String.format("%02x", b))
        return sb.toString()
    }

}