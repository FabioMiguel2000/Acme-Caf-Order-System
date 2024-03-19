package com.feup.coffee_order_application

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.feup.coffee_order_application.services.HttpHandlerClass
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val imageView: ImageView = findViewById(R.id.logo)
        imageView.setBackgroundResource(R.drawable.logoshop)
        var username = findViewById<EditText>(R.id.email_value)
        var password = findViewById<EditText>(R.id.password_value)
        val loginBtn: Button = findViewById(R.id.btn_login)
        val http_handler =  HttpHandlerClass.getInstance()
        loginBtn?.setOnClickListener{(
            //passar para assync ao inves de thread
            thread {
                var response = http_handler.login(this, http_handler._baseUrl, username.text.toString(), password.text.toString())
            }
        )}
    }
}