package com.feup.coffee_order_application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        val registerBtn: TextView = findViewById(R.id.btn_register)
        val http_handler =  HttpHandlerClass.getInstance()
        loginBtn?.setOnClickListener{
            http_handler.login(this.baseContext, username.text.toString(), password.text.toString())
            //start new activity here
        }

        registerBtn?.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java);
            startActivity(intent);
        }
    }
}