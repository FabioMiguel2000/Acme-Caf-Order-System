package com.feup.coffee_order_application.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.core.crypto.CryptoKeys
import com.feup.coffee_order_application.core.service.AuthManager

class RegisterActivity : AppCompatActivity() {
    private var generated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val crypto = CryptoKeys()
        generated = crypto.entry != null

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle("Register")
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val name = findViewById<EditText>(R.id.name_value)
        val email = findViewById<EditText>(R.id.email_value)
        val nif = findViewById<EditText>(R.id.nif_value)
        val password = findViewById<EditText>(R.id.password_value)
        val passwordConfirmation = findViewById<EditText>(R.id.passwordconfirmation_value)
        val btnRegister: Button = findViewById(R.id.btn_register)

        btnRegister.setOnClickListener{
            if(!generated){
                generated = crypto.generateAndStoreKeys()
            }

            val isValid = validateForm(name.text.toString(), email.text.toString(), nif.text.toString(), password.text.toString(), passwordConfirmation.text.toString(), generated)
            if (!isValid)
                return@setOnClickListener

            val publicKey = crypto.getPubKey()
            if (publicKey == null) {
                Toast.makeText(this, "Error generating keys", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val auth = AuthManager()
            auth.register(this.baseContext, name.text.toString(), email.text.toString(), nif.text.toString(), password.text.toString(), crypto.pubKeyToPem(publicKey), object :
                AuthManager.RegistrationCallback {

                override fun onRegistrationSuccess() {
                    name.text.clear()
                    email.text.clear()
                    nif.text.clear()
                    password.text.clear()
                    passwordConfirmation.text.clear()

                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    startActivity(intent)

                    finish()
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish();
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun validateForm(name:String, email:String, nif: String, password: String, passwordConfirmation: String, generated: Boolean): Boolean {

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(nif) || TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfirmation)){
            Toast.makeText(this, "Incorrect form fill", Toast.LENGTH_SHORT).show()
            return false
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Incorrect email format", Toast.LENGTH_SHORT).show()
            return false
        }

        if(password != passwordConfirmation){
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
            return false
        }

        if(!generated){
            Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}
