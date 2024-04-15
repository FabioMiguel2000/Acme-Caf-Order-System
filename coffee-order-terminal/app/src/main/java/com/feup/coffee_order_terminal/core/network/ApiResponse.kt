package com.feup.coffee_order_terminal.core.network

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T
)