package com.feup.coffee_order_application.core.service

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.feup.coffee_order_application.core.crypto.PubKey
import com.feup.coffee_order_application.core.network.ApiResponse
import com.feup.coffee_order_application.ui.activity.MainActivity
import com.feup.coffee_order_application.core.network.HttpHandlerClass
import com.feup.coffee_order_application.domain.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthManager {
    fun login(context: Context, username: String, password: String, publicKeyPem: String, onSuccess: () -> Unit) {
        val body = mapOf("email" to username, "password" to password, "publicKey" to publicKeyPem)
        HttpHandlerClass.getInstance().retrofitBuilder.login(body).enqueue(object : Callback<ApiResponse<User>> {
            override fun onResponse(call: Call<ApiResponse<User>>, response: Response<ApiResponse<User>>) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Login succeeded", Toast.LENGTH_LONG).show()
                    val userToken = response.body()?.data?._id

                    if(!userToken.isNullOrEmpty())
                        SessionManager(context).saveUserToken(userToken)

                    val intent = Intent(context, MainActivity::class.java);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Email or password is wrong", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ApiResponse<User>>, t: Throwable) {
                Log.d("error", "Login Request Error: ${t.message}")
            }
        })
    }

    fun register(context: Context, name: String, email: String, nif: String, password: String, publicKeyPem: String, callback: RegistrationCallback){


        //preparing request body
        val body = mapOf(
            "name" to name,
            "email" to email,
            "nif" to nif,
            "password" to password,
            "publicKey" to publicKeyPem
        )

        return HttpHandlerClass.getInstance().retrofitBuilder.register(body).enqueue(object : Callback<ApiResponse<User>> {
            override fun onResponse(
                call: Call<ApiResponse<User>>,
                response: Response<ApiResponse<User>>
            ) {
                if(response.code() == 201){
                    Toast.makeText(context, "Register succeeded", Toast.LENGTH_LONG).show()

                    val userToken = response.body()?.data?._id

                    if(!userToken.isNullOrEmpty())
                        SessionManager(context).saveUserToken(userToken)

                    callback.onRegistrationSuccess()
                } else {
                    Toast.makeText(context, "Verify your information", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ApiResponse<User>>, t: Throwable) {
                Log.d("error", "Register Request Error: " + t.message)
            }
        })
    }

    interface RegistrationCallback {
        fun onRegistrationSuccess()
    }
}