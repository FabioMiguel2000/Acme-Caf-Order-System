package com.feup.coffee_order_terminal.core.service

import com.feup.coffee_order_terminal.BuildConfig
import com.feup.coffee_order_terminal.domain.repository.OrderRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.feup.coffee_order_terminal.core.network.ApiInterface

object ServiceLocator {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService by lazy {
        retrofit.create(ApiInterface::class.java)
    }

    val orderRepository by lazy {
        OrderRepository(apiService)
    }

}