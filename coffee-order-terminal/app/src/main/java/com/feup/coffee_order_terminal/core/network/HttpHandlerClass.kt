package com.feup.coffee_order_terminal.core.network

import com.feup.coffee_order_terminal.BuildConfig
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
            val base = BuildConfig.API_BASE_URL
            val retBuilder =
                Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(base)
                    .build().create(ApiInterface::class.java)
            instance ?: HttpHandlerClass(base, retBuilder).also {
                instance = it
            }
        }
    }
}