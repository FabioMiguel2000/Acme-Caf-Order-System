package com.feup.coffee_order_terminal

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_terminal.models.Order
import com.feup.coffee_order_terminal.models.Product
import com.feup.coffee_order_terminal.models.ProductOrder
import com.feup.coffee_order_terminal.service.OrderApiCommunicator
import com.feup.coffee_order_terminal.service.ProductAdapter

class OrderValidatorActivity : AppCompatActivity() {
    private val products = mutableListOf<ProductOrder>()
    private val userNif: String = ""
    private val orderStatus: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_validator)

        //val order = intent.getStringExtra("orderId")
        val order = "70480c83631156e7179f33ac9ca87f11"//apagar depois isso
        val orderIdTextView : TextView = findViewById(R.id.order_number_id)
        orderIdTextView.text = order

        val btnValidate : Button = findViewById(R.id.btn_validate_order)
        btnValidate.setOnClickListener {
            val orderApiCommunicator = OrderApiCommunicator()
            orderApiCommunicator.validateOrder(this.baseContext, order)
        }

        val completeOrder = getOrder(order)
    }

    private fun getOrder(orderId: String){
        val orderApiCommunicator = OrderApiCommunicator()
        orderApiCommunicator.getOrder(this.baseContext, orderId) {
            order -> order.let {
                this.products.addAll(it!!.products)
                updateRecyclerView()
            }
            changeOrderInformation(order!!.status, order.client.nif)
            Log.e("products", order.products.toString())
        }
    }

    private fun updateRecyclerView() {
        val recyclerView = requireViewById<RecyclerView>(R.id.product_list)
        (recyclerView.adapter as? ProductAdapter)?.let { adapter ->
            adapter.products.clear()
            adapter.products.addAll(products)
            adapter.notifyDataSetChanged() // Notify the adapter of the data change - there's a pub sub inside
        }
    }

    private fun changeOrderInformation(status: String, nif: String){
        val orderStatus: TextView = findViewById(R.id.order_status)
        val orderUser: TextView = findViewById(R.id.order_owner)
        orderStatus.text = status
        orderUser.text = nif
    }
}