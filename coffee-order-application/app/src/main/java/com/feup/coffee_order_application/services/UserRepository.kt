package com.feup.coffee_order_application.services

import android.util.Log
import com.feup.coffee_order_application.models.ApiResponse
import com.feup.coffee_order_application.models.User
import retrofit2.Call
import javax.security.auth.callback.Callback

class UserRepository(private val api: ApiInterface) {
    fun getUserById (userId: String, callback: (User?)->Unit){
        val call = api.getUserById(userId)

        call.enqueue(object : retrofit2.Callback<ApiResponse<User>> {
            override fun onResponse(call: retrofit2.Call<ApiResponse<User>>, response: retrofit2.Response<ApiResponse<User>>) {
                if (response.isSuccessful) {
                    val user = response.body()?.data
                    Log.d("Response", response.body().toString())
                    callback(user)
                }
                else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ApiResponse<User>>, t: Throwable) {
                callback(null)
            }
        })
    }
}