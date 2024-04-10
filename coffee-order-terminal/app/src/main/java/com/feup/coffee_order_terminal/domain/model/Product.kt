package com.feup.coffee_order_terminal.domain.model

data class Product(
    val _id: String,
    val name: String,
    val price: Double,
    val imgURL: String,
    val category: String
)
