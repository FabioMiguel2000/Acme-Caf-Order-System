package com.feup.coffee_order_terminal.domain.repository

import com.feup.coffee_order_terminal.core.ApiResponse
import com.feup.coffee_order_terminal.domain.model.Order
import com.feup.coffee_order_terminal.network.ApiInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderRepository(private val api: ApiInterface) {
    fun getOrders(callback: (List<Order>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            api.getOrders().enqueue(object : Callback<ApiResponse<List<Order>>> {

                override fun onResponse(
                    call: Call<ApiResponse<List<Order>>>,
                    response: Response<ApiResponse<List<Order>>>
                ) {
                    callback(response.body()?.data)
                }

                override fun onFailure(call: Call<ApiResponse<List<Order>>>, t: Throwable) {
                    callback(null)
                }
            })
        }
    }
}
