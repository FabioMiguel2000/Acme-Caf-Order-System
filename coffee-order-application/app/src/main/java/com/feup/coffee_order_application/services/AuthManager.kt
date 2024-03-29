package com.feup.coffee_order_application.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.feup.coffee_order_application.models.ResponseApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthManager {
    val http_handler =  HttpHandlerClass.getInstance()
    fun login(context: Context, username: String, password: String){
        //preparing request body
        val body = mapOf(
            "email" to username,
            "password" to password
        )

        http_handler.retrofitBuilder.login(body).enqueue(object : Callback<ResponseApi> {
            override fun onResponse(
                call: Call<ResponseApi>,
                response: Response<ResponseApi>
            ) {
                if(response.code() == 200){
                    Toast.makeText(context, "Login succeeded", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Email or password is wrong", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                Log.d("error", "Login Request Error: " + t.message)
            }
        })
    }

    fun getProductCategories(){

    }
}