package com.feup.coffee_order_application.services

import com.feup.coffee_order_application.models.ResponseApi
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    /*@GET("users")
    fun getPosts(): retrofit2.Call<List<PostItem>>*/

    @POST("auth/login")
    @JvmSuppressWildcards
    fun login(@Body body: Map<String, Any>): retrofit2.Call<ResponseApi>
}