package com.feup.coffee_order_terminal.service

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.feup.coffee_order_terminal.core.network.ApiResponse
import com.feup.coffee_order_terminal.core.network.HttpHandlerClass
import com.feup.coffee_order_terminal.models.CoffeeVoucher
import com.feup.coffee_order_terminal.models.DiscountVoucher
import com.feup.coffee_order_terminal.models.Order
import com.feup.coffee_order_terminal.models.Product
import com.feup.coffee_order_terminal.models.ProductItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderApiComunicator {
    fun validateOrder(context: Context, client: String, status: String, products: List<ProductItem>, freeCoffeeVoucher: CoffeeVoucher, discountVoucher: DiscountVoucher/*, publicKey: String*/) {
        val body = mapOf(
            "client" to client,
            "status" to status,
            "products" to products,
            "freeCoffeVoucher" to freeCoffeeVoucher,
            "discountVoucher" to discountVoucher
        )

        HttpHandlerClass.getInstance().retrofitBuilder.createOrder(body).enqueue(object : Callback<ApiResponse<Order>> {
            override fun onResponse(
                call: Call<ApiResponse<Order>>,
                response: Response<ApiResponse<Order>>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(context, "Order validated", Toast.LENGTH_LONG).show()
                } else {
                  Toast.makeText(context, "Something went wrong, please try again later", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse<Order>>, t: Throwable) {
                 Log.d("error", "Register Request Error: " + t.message)
            }

        })
    }
}