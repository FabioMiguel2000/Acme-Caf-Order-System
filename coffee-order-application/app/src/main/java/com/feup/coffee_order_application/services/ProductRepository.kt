package com.feup.coffee_order_application.services

import android.util.Log
import com.feup.coffee_order_application.models.Category
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductRepository(private val api: ApiInterface) {
    fun fetchProductCategories(callback: (List<Category>?) -> Unit) {
        api.getProductCategories().enqueue(object : retrofit2.Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: retrofit2.Response<List<Category>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("ProductRepo", "Failed to fetch product categories: ${response.message()}")
                    callback(null)
                }
            }
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Log.e("ProductRepo", "Error fetching product categories", t)
                callback(null)
            }
        })
    }
}

object ServiceLocator {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService by lazy {
        retrofit.create(ApiInterface::class.java)
    }

    val productRepository by lazy {
        ProductRepository(apiService)
    }
}