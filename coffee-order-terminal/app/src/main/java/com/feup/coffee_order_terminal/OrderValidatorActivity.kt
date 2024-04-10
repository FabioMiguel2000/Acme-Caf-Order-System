package com.feup.coffee_order_terminal

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.feup.coffee_order_terminal.service.OrderApiCommunicator

class OrderValidatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_validator)

        val order = intent.getStringExtra("orderId")
        val orderIdTextView : TextView = findViewById(R.id.order_number_id)
        orderIdTextView.setText(order)
        val orderCom = OrderApiCommunicator()
        //run {
            orderCom.getOrder(this.baseContext, order.toString())
        //}

    }
}