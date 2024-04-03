package com.feup.coffee_order_application.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.feup.coffee_order_application.BuildConfig

object ServiceLocator {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
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

}