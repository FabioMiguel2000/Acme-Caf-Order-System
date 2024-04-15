package com.feup.coffee_order_terminal.core.utils

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import java.util.Locale

class DateFormatter {
    fun formatDate(date: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        originalFormat.timeZone = TimeZone.getTimeZone("UTC")

        val newFormat = SimpleDateFormat("dd MMMM yy", Locale.ENGLISH)

        val date = originalFormat.parse(date)
        val formattedDate = date?.let { newFormat.format(it) }

        return formattedDate ?: ""
    }
}