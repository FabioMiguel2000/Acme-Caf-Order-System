package com.feup.coffee_order_terminal.models

data class OrderResponse(
    val _id: String,
    //val products: List<Product>, TODO: REMOVE LATER
    val products: List<ProductOrder>,
    val client: Client,
    val status: String,
    val date: String,
    val freeCoffeeVoucher: CoffeeVoucher,
    val discountVoucher: DiscountVoucher,
    val total: String,
    val subtotal: String,
    val promotionDiscount: String
)
