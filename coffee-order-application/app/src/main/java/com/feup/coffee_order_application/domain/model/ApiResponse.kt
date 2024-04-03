package com.feup.coffee_order_application.domain.model

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T
)