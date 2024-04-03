package com.feup.coffee_order_application.models

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T
)