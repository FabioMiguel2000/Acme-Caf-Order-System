package com.feup.coffee_order_terminal.domain.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Voucher(
    val _id: String,
    val type: String,
    val client: User,
    val used: Boolean,
    val date: String,
    var isSelected: Boolean = false
): Parcelable{
    companion object {
        const val TYPE_DISCOUNT = "Discount"
        const val TYPE_FREE_COFFEE = "FreeCoffee"
    }
}
