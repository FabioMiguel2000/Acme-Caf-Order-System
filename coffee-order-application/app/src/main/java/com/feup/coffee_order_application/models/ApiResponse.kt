package com.feup.coffee_order_application.models

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T     //TODO: change to List<T> when all api responses are lists
)