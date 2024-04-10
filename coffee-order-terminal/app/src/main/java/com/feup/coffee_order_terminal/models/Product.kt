package com.feup.coffee_order_terminal.models

data class Product(
    val _id: String,
    val name: String,
    val price: Float,
    val imgURL: String,
    val category: Category
)
