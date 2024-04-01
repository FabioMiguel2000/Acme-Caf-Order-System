package com.feup.coffee_order_application.services

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.feup.coffee_order_application.MainActivity
import com.feup.coffee_order_application.RegisterActivity
import com.feup.coffee_order_application.models.ResponseApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthManager {
    fun login(context: Context, username: String, password: String, onSuccess: () -> Unit) {
        val body = mapOf("email" to username, "password" to password)
        HttpHandlerClass.getInstance().retrofitBuilder.login(body).enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Login succeeded", Toast.LENGTH_LONG).show()

                    val intent = Intent(context, MainActivity::class.java);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)

                } else {
                    Toast.makeText(context, "Email or password is wrong", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                Log.d("error", "Login Request Error: ${t.message}")
            }
        })
    }
}