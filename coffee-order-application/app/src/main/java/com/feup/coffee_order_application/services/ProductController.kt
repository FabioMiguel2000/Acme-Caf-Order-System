package com.feup.coffee_order_application.services

import android.util.Log
import com.feup.coffee_order_application.models.CategoryItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductController {
    val http_handler = HttpHandlerClass.getInstance()

    open fun getProductCategories(){
        try {
            http_handler.retrofitBuilder.getProductCategories().enqueue(object: Callback<List<CategoryItem>?> {
                override fun onResponse(
                    call: Call<List<CategoryItem>?>,
                    response: Response<List<CategoryItem>?>
                ) {
                    var resposta: List<CategoryItem>? = null
                    if(response.isSuccessful){
                        if(response != null) {
                            resposta = response.body()
                        }
                    }
                    val resultado = resposta
                }
                override fun onFailure(call: Call<List<CategoryItem>?>, t: Throwable) {
                    Log.e("result", "nok" + t.message)
                }
            })
        } catch (exception: Exception){
            Log.e("Erro", exception.toString())
        }
    }
}