package com.feup.coffee_order_application.domain.model

data class CartProduct (
    val name: String,
    val price: Double,
    val imageUrl: Int,
    val productCategory: String,
    var quantity: Int
)
