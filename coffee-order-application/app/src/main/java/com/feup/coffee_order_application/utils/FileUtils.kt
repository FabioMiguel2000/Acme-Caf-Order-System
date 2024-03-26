package com.feup.coffee_order_application.utils

import android.content.Context
import android.util.Log
import com.feup.coffee_order_application.models.CartProduct
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class FileUtils {
    companion object {
        private const val CART_FILE_NAME = "cart_items.json"

        fun saveCartToFile(cartProducts: List<CartProduct>, context: Context) {
            val gson = Gson()
            val jsonCart = gson.toJson(cartProducts)
            Log.d("FileUtils", "Saving cart to file: $jsonCart")
            context.openFileOutput(CART_FILE_NAME, Context.MODE_PRIVATE).use {
                it.write(jsonCart.toByteArray())
            }
        }

        fun readCartFromFile(context: Context): List<CartProduct> {
            val gson = Gson()
            val file = File(context.filesDir, CART_FILE_NAME)
            if (!file.exists()) return emptyList() // Return an empty list if the file doesn't exist
            val jsonCart = file.readText()
            Log.d("FileUtils", "Reading cart from file: $jsonCart")

            val type = object : TypeToken<List<CartProduct>>() {}.type
            return gson.fromJson(jsonCart, type)
        }
    }
}