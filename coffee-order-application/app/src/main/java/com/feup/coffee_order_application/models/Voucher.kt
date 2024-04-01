package com.feup.coffee_order_application.models

data class Voucher(
    val _id: String,
    val type: String,
    val client: User,
    val used: Boolean,
    val date: String,
    var isSelected: Boolean = false
)
