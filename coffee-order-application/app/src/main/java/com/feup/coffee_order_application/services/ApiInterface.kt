package com.feup.coffee_order_application.services

import com.feup.coffee_order_application.models.ApiResponse
import com.feup.coffee_order_application.models.ResponseApi
import com.feup.coffee_order_application.models.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    @POST("auth/login")
    @JvmSuppressWildcards
    fun login(@Body body: Map<String, Any>): retrofit2.Call<ResponseApi>
    @GET("users/{id}")
    fun getUserById(@Path("id") userId: String): retrofit2.Call<ApiResponse<User>>
}