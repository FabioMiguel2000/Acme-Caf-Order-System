package com.feup.coffee_order_terminal.models

data class Order(
    val _id: String,
    //val products: List<Product>, TODO: REMOVE LATER
    val products: List<ProductOrder>,
    val client: Client,
    val status: String,
    val date: String,
    val coffeeVoucher: CoffeeVoucher,
    val discountVoucher: DiscountVoucher,
    val total: String,
    val subtotal: String
)
