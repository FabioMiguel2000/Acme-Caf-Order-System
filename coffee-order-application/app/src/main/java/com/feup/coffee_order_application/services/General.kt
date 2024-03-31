package com.feup.coffee_order_application.services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.Base64

class General {

    open fun baseImageToBitMap(image: String): Bitmap? {
        val imageBytes = android.util.Base64.decode(image, android.util.Base64.DEFAULT)
        return  BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}