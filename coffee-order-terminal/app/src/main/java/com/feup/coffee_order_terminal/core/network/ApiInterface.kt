package com.feup.coffee_order_terminal.core.network

import com.feup.coffee_order_terminal.domain.model.Order
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("orders")
    fun getOrders(): Call<ApiResponse<List<Order>>>
}