package com.feup.coffee_order_terminal.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartProduct (
    var product: Product,
    var quantity: Int
): Parcelable
