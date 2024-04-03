package com.feup.coffee_order_application.core.network

import com.feup.coffee_order_application.domain.model.Category
import com.feup.coffee_order_application.domain.model.ResponseApi
import com.feup.coffee_order_application.domain.model.User
import com.feup.coffee_order_application.domain.model.Voucher
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @POST("auth/register")
    @JvmSuppressWildcards
    fun register(@Body body: Map<String, Any>): Call<ResponseApi>
    @POST("auth/login")
    @JvmSuppressWildcards
    fun login(@Body body: Map<String, Any>): Call<ResponseApi>
    @GET("productCategories")
    fun getProductCategories(): Call<ApiResponse<List<Category>>>

    @GET("users/{id}")
    fun getUserById(@Path("id") userId: String): Call<ApiResponse<User>>
    @GET("vouchers/client")
    fun getUserVouchers(@Query("client") clientId: String): Call<ApiResponse<List<Voucher>>>


}