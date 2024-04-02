package com.feup.coffee_order_application

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.feup.coffee_order_application.crypto.CryptoKeys
import com.feup.coffee_order_application.services.AuthManager
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

        var name = findViewById<EditText>(R.id.name_value)
        var email = findViewById<EditText>(R.id.email_value)
        var nif = findViewById<EditText>(R.id.nif_value)
        var password = findViewById<EditText>(R.id.password_value)
        var passwordConfirmation = findViewById<EditText>(R.id.passwordconfirmation_value)
        var btnRegister: Button = findViewById(R.id.btn_register)

        btnRegister?.setOnClickListener{
            if(!generated){
                generated = crypto.generateAndStoreKeys()
            }

            val isValid = validateForm(name.text.toString(), email.text.toString(), nif.text.toString(), password.text.toString(), passwordConfirmation.text.toString(), generated)
            if (!isValid)
                return@setOnClickListener

            val auth = AuthManager()
            auth.register(this.baseContext, name.text.toString(), email.text.toString(), nif.text.toString(), password.text.toString(), crypto.getPublicKey(), object :
                AuthManager.RegistrationCallback {

                override fun onRegistrationSuccess() {
                    //TODO: Send user to home activity
                    name.text.clear()
                    email.text.clear()
                    nif.text.clear()
                    password.text.clear()
                    passwordConfirmation.text.clear()
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

    fun validateForm(name:String, email:String, nif: String, password: String, passwordConfirmation: String, generated: Boolean): Boolean {

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
