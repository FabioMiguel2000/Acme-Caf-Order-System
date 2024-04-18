package com.feup.coffee_order_terminal.domain.repository

import android.util.Log
import com.feup.coffee_order_terminal.core.network.ApiInterface
import com.feup.coffee_order_terminal.core.network.ApiResponse
import com.feup.coffee_order_terminal.domain.model.Order
import retrofit2.Call
import retrofit2.Response


class OrderRepository(private val api: ApiInterface) {
    fun getOrders(callback: (List<Order>?) -> Unit) {
        api.getOrders().enqueue(object : retrofit2.Callback<ApiResponse<List<Order>>> {
            override fun onResponse(
                call: Call<ApiResponse<List<Order>>>,
                response: Response<ApiResponse<List<Order>>>
            ) {
                if (response.isSuccessful) {
                    callback(response.body()?.data)
                } else {
                    Log.e("ProductRepo", "Failed to get orders: ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ApiResponse<List<Order>>>, t: Throwable) {
                Log.e("ProductRepo", "Error getting orders", t)
                callback(null)
            }
        })
    }
}