package com.feup.coffee_order_application.core.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageUtils {

    open fun baseImageToBitMap(image: String): Bitmap? {
        val imageBytes = android.util.Base64.decode(image, android.util.Base64.DEFAULT)
        return  BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    fun loadBase64ImageIntoView(base64Image: String, imageView: ImageView) {
        val base64Section = if (base64Image.startsWith("data:image")) "" else "data:image/jpeg;base64,"
        val fullBase64String = "$base64Section$base64Image"
        val imageBytes = Base64.decode(fullBase64String.substring(fullBase64String.indexOf(",") + 1), Base64.DEFAULT)

        Glide.with(imageView.context)
            .asBitmap()
            .load(imageBytes)
            .into(imageView)
    }
}