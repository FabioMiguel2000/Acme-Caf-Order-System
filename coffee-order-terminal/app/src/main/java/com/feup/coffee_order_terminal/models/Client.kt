package com.feup.coffee_order_terminal.models

data class Client(
    val __v: Int,
    val _id: String,
    val accumulatedCoffeeBuys: Int,
    val accumulatedExpress: Float,
    val email: String,
    val name: String,
    val nif: String,
)