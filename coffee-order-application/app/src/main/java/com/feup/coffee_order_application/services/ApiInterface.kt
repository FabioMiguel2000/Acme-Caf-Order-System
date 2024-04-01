package com.feup.coffee_order_application.services

import com.feup.coffee_order_application.models.ApiResponse
import com.feup.coffee_order_application.models.ResponseApi
import com.feup.coffee_order_application.models.User
import com.feup.coffee_order_application.models.VoucherData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @POST("auth/login")
    @JvmSuppressWildcards
    fun login(@Body body: Map<String, Any>): Call<ResponseApi>
    @GET("users/{id}")
    fun getUserById(@Path("id") userId: String): Call<ApiResponse<User>>
    @GET("vouchers/client")
    fun getUserVouchers(@Query("client") clientId: String): Call<ApiResponse<List<VoucherData>>>
}