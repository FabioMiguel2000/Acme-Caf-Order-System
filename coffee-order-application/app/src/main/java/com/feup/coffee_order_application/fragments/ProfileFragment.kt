package com.feup.coffee_order_application.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.models.ApiResponse
import com.feup.coffee_order_application.models.ResponseApi
import com.feup.coffee_order_application.models.User
import com.feup.coffee_order_application.services.ApiInterface
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileFragment : Fragment() {
    private var user_id: String = "31ca6621550a71fdb4629390d1d264a2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Hi User!"

        fetchUserData()
    }

    private fun fetchUserData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiInterface::class.java)
        val call = apiService.getUserById(user_id)

        call.enqueue(object : retrofit2.Callback<ApiResponse<User>> {
            override fun onResponse(call: retrofit2.Call<ApiResponse<User>>, response: retrofit2.Response<ApiResponse<User>>) {
                if (response.isSuccessful) {
                    val user = response.body()?.data
                    Log.d("Response", response.body().toString())

                    Log.d("User", user.toString())
//                    user?.let {
//                        updateUI(it)
//                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse<User>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun updateUI(user: User) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Hi ${user.name}!"
        // Update other UI elements with user data as needed
    }
}