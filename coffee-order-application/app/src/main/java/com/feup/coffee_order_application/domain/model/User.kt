package com.feup.coffee_order_application.domain.model
data class User(
    val __v: Int,
    val _id: String,
    val accumulatedCoffeeBuys: Double,
    val accumulatedExpenses: Double,
    val email: String,
    val name: String,
    val nif: String
)