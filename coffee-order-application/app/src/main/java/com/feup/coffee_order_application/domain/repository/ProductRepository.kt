package com.feup.coffee_order_application.domain.repository

import android.util.Log
import com.feup.coffee_order_application.domain.model.ApiResponse
import com.feup.coffee_order_application.domain.model.Category
import com.feup.coffee_order_application.core.network.ApiInterface
import retrofit2.Call
import retrofit2.Response

class ProductRepository(private val api: ApiInterface) {
    fun fetchProductCategories(callback: (List<Category>?) -> Unit) {
        api.getProductCategories().enqueue(object : retrofit2.Callback<ApiResponse<List<Category>>> {
            override fun onResponse(
                call: Call<ApiResponse<List<Category>>>,
                response: Response<ApiResponse<List<Category>>>
            ) {
                if (response.isSuccessful) {
                    callback(response.body()?.data)
                } else {
                    Log.e("ProductRepo", "Failed to fetch product categories: ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ApiResponse<List<Category>>>, t: Throwable) {
                Log.e("ProductRepo", "Error fetching product categories", t)
                callback(null)
            }
        })
    }
}

