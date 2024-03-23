package com.feup.coffee_order_application.models

data class ResponseApi(
    val `data`: Data,
    val message: String,
    val success: Boolean
)