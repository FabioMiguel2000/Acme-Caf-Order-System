package com.feup.coffee_order_application.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/api/")
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