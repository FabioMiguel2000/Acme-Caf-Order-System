package com.feup.coffee_order_terminal

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_terminal.models.ProductOrder
import com.feup.coffee_order_terminal.service.OrderApiCommunicator
import com.feup.coffee_order_terminal.ui.adapter.ProductAdapter

class OrderValidatorActivity : AppCompatActivity() {
    private val products = mutableListOf<ProductOrder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_validator)

        val adapter =  ProductAdapter(products)
        requireViewById<RecyclerView>(R.id.rv_product_list).apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

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

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)


    }

    /*
    * val adapter = CheckoutAdapter(order.products)

        binding.rvOrder.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }*/

    private fun getOrder(orderId: String){
        Log.e("testing_", "Hi")
        val orderApiCommunicator = OrderApiCommunicator()
        orderApiCommunicator.getOrder(this.baseContext, orderId) {
            order -> order.let {
                Log.e ("showingprod", it!!.products.toString())
                this.products.addAll(it!!.products)
                updateRecyclerView()
            }
            changeOrderInformation(order!!.status, order.client.nif)
            Log.e("products", order.products.toString())
        }
    }

    private fun updateRecyclerView() {
        val recyclerView = requireViewById<RecyclerView>(R.id.rv_product_list)
        (recyclerView.adapter as? ProductAdapter)?.let { adapter ->

            adapter.products.clear()
            Log.e("productUpdate", this.toString())
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