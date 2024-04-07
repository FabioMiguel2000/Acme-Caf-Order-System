package com.feup.coffee_order_terminal.models

data class ProductItem(
    val __v: Int,
    val _id: String,
    val category: Category,
    val imgURL: String,
    val name: String,
    val price: Double
)