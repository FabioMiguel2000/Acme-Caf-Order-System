package com.feup.coffee_order_terminal.models

data class ProductOrder (
    val _id: String,
    val quantity: Int,
    val product : Product
)