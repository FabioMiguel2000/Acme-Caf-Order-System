package com.feup.coffee_order_application.domain.repository

import android.util.Log
import com.feup.coffee_order_application.core.network.ApiInterface
import com.feup.coffee_order_application.core.network.ApiResponse
import com.feup.coffee_order_application.domain.model.Order
import com.feup.coffee_order_application.domain.model.OrderRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderRepository(private val api: ApiInterface) {
    fun createOrder(orderRequest: OrderRequest, callback: (Boolean, Int) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            api.createOrder(orderRequest).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    callback(response.isSuccessful, response.code())
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("OrderRepo", "Error sending order", t)
                    callback(false, 0)
                }
            })
        }
    }
    fun getOrdersByClientId(clientId: String, callback: (List<Order>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            api.getClientOrders(clientId).enqueue(object : Callback<ApiResponse<List<Order>>> {

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
