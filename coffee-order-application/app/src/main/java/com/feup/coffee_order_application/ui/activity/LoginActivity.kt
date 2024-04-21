package com.feup.coffee_order_application.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.core.service.AuthManager
import com.feup.coffee_order_application.core.crypto.CryptoKeys

class LoginActivity : AppCompatActivity() {
    private var generated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val imageView: ImageView = findViewById(R.id.logo)
        imageView.setBackgroundResource(R.drawable.logoshop)

        val crypto = CryptoKeys()


        val username = findViewById<EditText>(R.id.email_value)
        val password = findViewById<EditText>(R.id.password_value)
        val loginBtn: Button = findViewById(R.id.btn_login)
        val registerBtn: TextView = findViewById(R.id.btn_register)
        loginBtn.setOnClickListener{
            crypto.generateAndStoreKeys()

            val publicKey = crypto.getPubKey()
            val auth = AuthManager()
            auth.login(this.baseContext, username.text.toString(), password.text.toString(), crypto.pubKeyToPem(publicKey)) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}