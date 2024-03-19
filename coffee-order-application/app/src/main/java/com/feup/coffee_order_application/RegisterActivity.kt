package com.feup.coffee_order_application

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.concurrent.thread

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var name = findViewById<EditText>(R.id.name_value)
        var email = findViewById<EditText>(R.id.email_value)
        var nif = findViewById<EditText>(R.id.nif_value)
        var password = findViewById<EditText>(R.id.password_value)
        var passwordConfirmation = findViewById<EditText>(R.id.passwordconfirmation_value)
        val btnRegister: Button = findViewById(R.id.btn_register)

    }
}