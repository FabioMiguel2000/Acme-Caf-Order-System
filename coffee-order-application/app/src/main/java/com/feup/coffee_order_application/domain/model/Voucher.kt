package com.feup.coffee_order_application.domain.model

data class Voucher(
    val _id: String,
    val type: String,
    val client: User,
    val used: Boolean,
    val date: String,
    var isSelected: Boolean = false
){
    companion object {
        const val TYPE_DISCOUNT = "Discount"
        const val TYPE_FREE_COFFEE = "FreeCoffee"
    }
}
