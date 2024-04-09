package com.feup.coffee_order_application.core.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.feup.coffee_order_application.BuildConfig
import com.feup.coffee_order_application.core.crypto.CryptoKeys
import com.feup.coffee_order_application.core.interceptors.HeaderInterceptor
import com.feup.coffee_order_application.core.network.ApiInterface
import com.feup.coffee_order_application.domain.repository.OrderRepository
import com.feup.coffee_order_application.domain.repository.ProductRepository
import com.feup.coffee_order_application.domain.repository.UserRepository
import okhttp3.OkHttpClient

object ServiceLocator {

    private val cryptoKeys = CryptoKeys()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor(cryptoKeys)) // Add your interceptor
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val apiService by lazy {
        retrofit.create(ApiInterface::class.java)
    }

    val userRepository by lazy {
        UserRepository(apiService)
    }
    val productRepository by lazy {
        ProductRepository(apiService)
    }

    val orderRepository by lazy {
        OrderRepository(apiService)
    }

}