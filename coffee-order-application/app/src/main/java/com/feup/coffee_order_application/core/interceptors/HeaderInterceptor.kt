package com.feup.coffee_order_application.core.interceptors

import com.feup.coffee_order_application.core.crypto.CryptoKeys
import android.util.Base64
import okhttp3.Interceptor
import okhttp3.Response
import java.security.KeyStore
import com.feup.coffee_order_application.core.crypto.Crypto
import okhttp3.Request
import android.util.Log


class HeaderInterceptor(private val cryptoKeys: CryptoKeys) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val targetEndpoints = listOf("/api/orders/create","/api/orders/client", "/api/vouchers/client")
        val timestamp = System.currentTimeMillis().toString()

        Log.d("Interceptor", "Request path: ${originalRequest.url.encodedPath}")

        if (targetEndpoints.contains(originalRequest.url.encodedPath)) {
            val dataToSign = constructDataForSigning(originalRequest, timestamp)
            val signature = signContent(dataToSign)
            val signedRequest = originalRequest.newBuilder()
                .header("x-signature", signature)
                .header("x-timestamp", timestamp)
                .build()
            return chain.proceed(signedRequest)
        }
        return chain.proceed(originalRequest)
    }

    private fun signContent(data: String): String {
        try {
            // Retrieve the private key
            val privateKeyEntry = cryptoKeys.entry as KeyStore.PrivateKeyEntry

            val signature = java.security.Signature.getInstance(Crypto.SIGN_ALGO)
            signature.initSign(privateKeyEntry.privateKey)
            signature.update(data.toByteArray())
            val signedBytes = signature.sign()
            return Base64.encodeToString(signedBytes, Base64.NO_WRAP)
        } catch (e: Exception) {
            throw RuntimeException("Error signing data", e)
        }
    }


    private fun constructCreateOrderData(originalRequest: Request): String {
        val body = originalRequest.body?.let {
            val buffer = okio.Buffer()
            it.writeTo(buffer)
            buffer.readUtf8()
        } ?: ""

        return "$body"
    }

    private fun constructDataForSigning(originalRequest: Request, timestamp: String): String {
        val client = originalRequest.url.queryParameter("client")

        return when (originalRequest.url.encodedPath) {
            "/api/orders/create" -> constructCreateOrderData(originalRequest) // Assuming this includes the timestamp
            "/api/orders/client", "/api/vouchers/client" -> "$client&$timestamp"
            else -> throw IllegalArgumentException("Unexpected path: ${originalRequest.url.encodedPath}")
        }
    }
}