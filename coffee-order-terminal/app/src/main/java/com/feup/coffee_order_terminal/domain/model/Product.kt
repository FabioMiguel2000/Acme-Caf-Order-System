package com.feup.coffee_order_terminal.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val _id: String,
    val name: String,
    val price: Double,
    val imgURL: String,
    val category: String
): Parcelable
