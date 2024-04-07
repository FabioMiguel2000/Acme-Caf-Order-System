package com.feup.coffee_order_application.core.utils

import android.content.Context
import com.feup.coffee_order_application.domain.model.Order
import com.google.gson.Gson
import java.io.File

class OrderStorageUtils {
    companion object {
        private const val ORDER_FILE_NAME = "cart_order.json"
        fun saveOrderToFile(order: Order, context: Context) {
            val gson = Gson()
            val jsonOrder = gson.toJson(order)
            context.openFileOutput(ORDER_FILE_NAME, Context.MODE_PRIVATE).use {
                it.write(jsonOrder.toByteArray())
            }
        }

        fun readOrderFromFile(context: Context): Order {
            val gson = Gson()
            val file = File(context.filesDir, ORDER_FILE_NAME)
            if (!file.exists()) return Order()

            val jsonOrder = file.readText()
            return gson.fromJson(jsonOrder, Order::class.java)
        }

        fun clearOrderFile(context: Context) {
            val file = File(context.filesDir, ORDER_FILE_NAME)
            if (file.exists()) {
                file.delete()
            }
        }
    }
}