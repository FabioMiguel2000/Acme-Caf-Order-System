package com.feup.coffee_order_application.services

import android.util.Log
import java.net.HttpURLConnection
import java.net.URL

class HttpHandlerClass private  constructor(baseUrl : String ){
    var _baseUrl: String = baseUrl
        get(){
            return field
        }
    fun testApiConnection(address: String){
        var url: URL? = null
        var urlConnection: HttpURLConnection? = null
        try {
            url = URL(address)
            urlConnection = (url.openConnection() as HttpURLConnection?)?.apply {
                doInput = true
                setRequestProperty("Content-Type", "application/json")
                useCaches = false
                connectTimeout = 500

                if(responseCode == 200){
                    Log.e("error", "OK")
                } else {
                    Log.e("error", "NOK")
                }
            }
        } catch (e: Exception){
            throw e
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect()
        }
    }
    companion object {
        @Volatile private var instance: HttpHandlerClass? = null
        //http://localhost:3000/
        //https://myfakeapi.com/api/cars/1
        fun getInstance() = instance ?: synchronized(this) {
            instance?:HttpHandlerClass("https://myfakeapi.com/api/cars/1").also {
                instance = it
            }
        }


    }
}
