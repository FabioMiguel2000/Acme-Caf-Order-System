package com.feup.coffee_order_application

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.feup.coffee_order_application.services.HttpHandlerClass

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        val imageView: ImageView = findViewById(R.id.logo)
        imageView.setBackgroundResource(R.drawable.logoshop)

        val http_hand =  HttpHandlerClass.getInstance()
        var url = http_hand._baseUrl
        http_hand.testApiConnection(http_hand._baseUrl)
        var teste = "Ola"
    }
}