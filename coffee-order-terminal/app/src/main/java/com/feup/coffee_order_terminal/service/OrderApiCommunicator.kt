package com.feup.coffee_order_terminal.service

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.feup.coffee_order_terminal.OrderValidatorActivity
import com.feup.coffee_order_terminal.core.network.ApiResponse
import com.feup.coffee_order_terminal.core.network.HttpHandlerClass
import com.feup.coffee_order_terminal.models.CoffeeVoucher
import com.feup.coffee_order_terminal.models.DiscountVoucher
import com.feup.coffee_order_terminal.models.Order
import com.feup.coffee_order_terminal.models.ProductCartItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderApiCommunicator {
    fun createOrder(context: Context, client: String, status: String, products: List<ProductCartItem>, freeCoffeeVoucher: CoffeeVoucher?, discountVoucher: DiscountVoucher?/*, publicKey: String*/) {

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
                if(response.code() == 201){
                    Toast.makeText(context, "Order created", Toast.LENGTH_LONG).show()
                    val response = response.body()?.data;
                    val _id = response?._id
                    val intent = Intent(context, OrderValidatorActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("orderId", _id)
                    context.startActivity(intent)
                } else {
                  Toast.makeText(context, "Something went wrong, please try again later", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ApiResponse<Order>>, t: Throwable) {
                 Log.d("error", "Unable to process request: " + t.message)
            }
        })
    }
}