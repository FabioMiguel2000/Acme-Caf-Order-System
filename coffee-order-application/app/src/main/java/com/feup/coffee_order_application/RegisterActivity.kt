package com.feup.coffee_order_application

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.feup.coffee_order_application.services.HttpHandlerClass
import kotlin.concurrent.thread

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle("Register")
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        var name = findViewById<EditText>(R.id.name_value)
        var email = findViewById<EditText>(R.id.email_value)
        var nif = findViewById<EditText>(R.id.nif_value)
        var password = findViewById<EditText>(R.id.password_value)
        var passwordConfirmation = findViewById<EditText>(R.id.passwordconfirmation_value)
        val btnRegister: Button = findViewById(R.id.btn_register)

        val http_handler =  HttpHandlerClass.getInstance()
        btnRegister?.setOnClickListener{}
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish();
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
