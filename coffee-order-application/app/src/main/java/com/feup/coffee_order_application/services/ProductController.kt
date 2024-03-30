package com.feup.coffee_order_application.services

import android.util.Log
import com.feup.coffee_order_application.models.CategoryItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductController {
    val http_handler = HttpHandlerClass.getInstance();

    open fun getProductCategories(){
        try {
            http_handler.retrofitBuilder.getProductCategories().enqueue(object: Callback<List<CategoryItem>?> {
                override fun onResponse(
                    call: Call<List<CategoryItem>?>,
                    response: Response<List<CategoryItem>?>
                ) {
                    Log.d("result", "ok")
                    val response = response.body()!!
                }

                override fun onFailure(call: Call<List<CategoryItem>?>, t: Throwable) {
                    Log.d("result", "nok")
                }
            })
        } catch (exception: Exception){
            Log.e("Erro", exception.toString())
        }

    }
}