package com.feup.coffee_order_terminal.models

data class Order(
    val products: List<Product>,
    val client: String,
    val status: String,
    val coffeeVoucher: CoffeeVoucher,
    val discountVoucher: DiscountVoucher
)
