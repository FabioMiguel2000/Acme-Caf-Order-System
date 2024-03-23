package com.feup.coffee_order_application.services
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.feup.coffee_order_application.models.ResponseApi
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpHandlerClass private  constructor(baseUrl : String ){
    var _baseUrl: String = baseUrl
        get(){
            return field
        }

    companion object {
        @Volatile private var instance: HttpHandlerClass? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance?:HttpHandlerClass("http://192.168.1.97:3000/").also {
                instance = it
            }
        }
    }

    //instance of retrofit
    val refrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("http://192.168.1.97:3000/api/").build().create(ApiInterface::class.java)
    fun login(context: Context, username: String, password: String){
        //preparing request body
        val body = mapOf(
            "email" to username,
            "password" to password
        )
        //send the request
        refrofitBuilder.login(body).enqueue(object : Callback<ResponseApi>{
            override fun onResponse(
                call: Call<ResponseApi>,
                response: Response<ResponseApi>
            ) {
                if(response.code() == 200){
                    Toast.makeText(context, "Login succeded", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Email or password is wrong", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                Log.d("error", "erro no login" + t.message)
            }
        })
    }
}
