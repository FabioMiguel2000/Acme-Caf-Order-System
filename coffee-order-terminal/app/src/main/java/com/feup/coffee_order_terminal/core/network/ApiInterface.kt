package com.feup.coffee_order_terminal.core.network

import com.feup.coffee_order_terminal.models.Order
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    @POST("orders/create")
    @JvmSuppressWildcards
    fun createOrder(@Body body: Map<String, Any?>): retrofit2.Call<ApiResponse<Order>>
    @POST("orders/validation/{id}")
    fun validateOrder(@Path("id") orderId: String): Call<ApiResponse<Order>>
}