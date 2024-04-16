package com.feup.coffee_order_terminal.service

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.feup.coffee_order_terminal.core.network.ApiResponse
import com.feup.coffee_order_terminal.core.network.HttpHandlerClass
import com.feup.coffee_order_terminal.domain.model.CartProduct
import com.feup.coffee_order_terminal.domain.model.Order
import com.feup.coffee_order_terminal.domain.model.SimplifiedCartProduct

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderApiCommunicator {
    fun createOrder(context: Context, client: String, status: String, products: List<SimplifiedCartProduct>, freeCoffeeVoucher: String?, discountVoucher: String?, callback: (Order?) -> Unit) {

        val body = mapOf(
            "client" to client,
            "status" to status,
            "products" to products,
            "freeCoffeeVoucher" to freeCoffeeVoucher,
            "discountVoucher" to discountVoucher
        )

        HttpHandlerClass.getInstance().retrofitBuilder.createOrder(body).enqueue(object : Callback<ApiResponse<Order>> {
            override fun onResponse(
                call: Call<ApiResponse<Order>>,
                response: Response<ApiResponse<Order>>
            ) {
                if(response.code() == 201){
                    Toast.makeText(context, "Order created", Toast.LENGTH_LONG).show()
                    val response = response.body()?.data;
                    callback(response)
                } else {
                    Log.e("errorStatus", response.code().toString())
                    callback(null)
                  Toast.makeText(context, "Something went wrong, please try again later", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ApiResponse<Order>>, t: Throwable) {
                 Log.d("error", "Unable to process request: " + t.message)


            }
        })
    }

    fun getOrder(context: Context, orderId: String, callback: (Order?)->Unit){
        HttpHandlerClass.getInstance().retrofitBuilder.getOrder(orderId).enqueue(object : Callback<ApiResponse<Order>> {
            override fun onResponse(
                call: Call<ApiResponse<Order>>,
                response: Response<ApiResponse<Order>>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Operation done successfully", Toast.LENGTH_LONG).show()
                    callback(response.body()?.data)
                } else {
                    Toast.makeText(context, "Something went wrong, unable to get order", Toast.LENGTH_LONG).show()
                    callback(null) // Consider passing specific error information
                }
            }
            override fun onFailure(call: Call<ApiResponse<Order>>, t: Throwable) {
                Toast.makeText(context, "Something went wrong, unable to get order", Toast.LENGTH_LONG).show()
                callback(null) // Consider passing specific error information
            }
        })
    }

    fun validateOrder(context: Context, orderId: String){
        HttpHandlerClass.getInstance().retrofitBuilder.validateOrder(orderId).enqueue(object : Callback<ApiResponse<Order>>{
            override fun onResponse(
                call: Call<ApiResponse<Order>>,
                response: Response<ApiResponse<Order>>
            ) {
                if(response.code() == 201){
                    Toast.makeText(context, "Order validated", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Something went wrong, order not validated", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse<Order>>, t: Throwable) {
                Toast.makeText(context, "Not internet connection", Toast.LENGTH_LONG).show()
            }
        })
    }
}