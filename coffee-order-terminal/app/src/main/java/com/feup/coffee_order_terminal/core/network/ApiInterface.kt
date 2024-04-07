package com.feup.coffee_order_terminal.core.network

import com.feup.coffee_order_terminal.models.Order
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("/orders/create")
    @JvmSuppressWildcards
    fun createOrder(@Body body: Map<String, Any>): retrofit2.Call<ApiResponse<Order>>
}