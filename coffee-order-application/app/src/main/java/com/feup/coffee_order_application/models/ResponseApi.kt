package com.feup.coffee_order_application.models

data class ResponseApi(
    val user: User,
    val message: String,
    val success: Boolean
)