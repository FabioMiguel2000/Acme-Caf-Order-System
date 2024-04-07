package com.feup.coffee_order_terminal.models

data class CoffeeVoucher(
    val _id: String,
    val client: Client,
    val date: String,
    val isSelected: Boolean,
    val type: String,
    val used: Boolean
)
