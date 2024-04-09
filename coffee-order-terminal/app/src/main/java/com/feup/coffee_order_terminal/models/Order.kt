package com.feup.coffee_order_terminal.models

data class Order(
    val _id: String,
    val products: List<Product>,
    val client: Client,
    val status: String,
    val coffeeVoucher: CoffeeVoucher,
    val discountVoucher: DiscountVoucher
)
