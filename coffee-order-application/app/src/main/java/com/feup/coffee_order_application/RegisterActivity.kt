package com.feup.coffee_order_application

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
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
        btnRegister?.setOnClickListener{(
                //passar para assync ao invés de thread
                thread {
                    //http_handler.testApiConnection(http_handler._baseUrl)
                    var response = http_handler.login(this, http_handler._baseUrl, username.text.toString(), password.text.toString())
                }
                )}

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
           //implement back button code
        }
        return super.onContextItemSelected(item)
    }
}
