package com.feup.coffee_order_terminal.service

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.feup.coffee_order_terminal.OrderValidatorFragment
import com.feup.coffee_order_terminal.R
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

                    //mostrar todas informações do pedido aqui
                    val dialog = Dialog(context)
                    dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    dialog.setContentView(R.layout.fragment_order_validator)
                    dialog.setTitle("Order information:")
                    var orderN = dialog.findViewById<TextView>(R.id.order_number)
                    orderN.text = "Texte"
                    val yesBtn = dialog.findViewById<Button>(R.id.btn_validate)
                    yesBtn.setOnClickListener{
                        //continuar aqui - validar pedido no servidor
                        dialog.dismiss()
                    }

                    val notBtn = dialog.findViewById<Button>(R.id.cancel)
                    notBtn.setOnClickListener{
                        dialog.dismiss()
                    }
                    dialog.show()
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