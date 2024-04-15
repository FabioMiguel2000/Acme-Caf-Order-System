package com.feup.coffee_order_terminal.core.network



import com.feup.coffee_order_terminal.domain.model.Order
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    @POST("orders/create")
    @JvmSuppressWildcards
    fun createOrder(@Body body: Map<String, Any?>): Call<ApiResponse<Order>>
    @POST("orders/validation/{id}")
    fun validateOrder(@Path("id") orderId: String): Call<ApiResponse<Order>>
    @GET("orders/{id}")
    fun getOrder(@Path("id") orderId: String): Call<ApiResponse<Order>>
    @GET("orders")
    fun getOrders(): Call<ApiResponse<List<Order>>>

}