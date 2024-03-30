package com.feup.coffee_order_application.services

import com.feup.coffee_order_application.models.CategoryItem
import com.feup.coffee_order_application.models.ResponseApi
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @POST("auth/login")
    @JvmSuppressWildcards
    fun login(@Body body: Map<String, Any>): retrofit2.Call<ResponseApi>

    @GET("productCategories")
    fun getProductCategories(): retrofit2.Call<List<CategoryItem>>
}