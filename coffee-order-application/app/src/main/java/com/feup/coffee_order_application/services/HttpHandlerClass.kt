package com.feup.coffee_order_application.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpHandlerClass private constructor(baseUrl: String, retrofitBuilder: ApiInterface) {
    var _baseUrl: String = baseUrl
        get() {
            return field
        }

    val retrofitBuilder: ApiInterface = retrofitBuilder
        get() {
            return field
        }

    companion object {
        @Volatile
        private var instance: HttpHandlerClass? = null
        fun getInstance() = instance ?: synchronized(this) {
            val base = "http://10.0.2.2:3000/api/"
            val retBuilder =
                Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(base)
                    .build().create(ApiInterface::class.java)
            instance ?: HttpHandlerClass(base, retBuilder).also {
                instance = it
            }
        }
    }
}
