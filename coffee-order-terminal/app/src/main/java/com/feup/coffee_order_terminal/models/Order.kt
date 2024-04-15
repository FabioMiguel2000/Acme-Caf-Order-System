package com.feup.coffee_order_terminal.models

data class Order(
    val _id: String,
    val products: List<ProductOrder>,
    val client: Client,
    val status: String,
    val date: String,
    val coffeeVoucher: String,
    val discountVoucher: String,
    val total: String,
    val subtotal: String,
    val promotionDiscount: String
)
