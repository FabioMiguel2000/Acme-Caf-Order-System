package com.feup.coffee_order_terminal.core.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.feup.coffee_order_terminal.R

class ImageUtils {

    open fun baseImageToBitMap(image: String): Bitmap? {
        val imageBytes = Base64.decode(image, Base64.DEFAULT)
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

    private fun convertGoogleDriveLinkToDirectLink(driveLink: String): String? {
        val fileId = Regex("https://drive.google.com/file/d/(.*?)/view").find(driveLink)?.groupValues?.get(1)
        return fileId?.let { "https://drive.google.com/uc?export=view&id=$it" }
    }

    fun loadImageFromUrlIntoView(imageUrl: String, imageView: ImageView) {
        val directImageUrl = convertGoogleDriveLinkToDirectLink(imageUrl)
        directImageUrl?.let { url ->
            Glide.with(imageView.context)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error_image)
                .into(imageView)
        } ?: run {
            Glide.with(imageView.context)
                .load(R.drawable.error_image)
                .into(imageView)
        }
    }

}