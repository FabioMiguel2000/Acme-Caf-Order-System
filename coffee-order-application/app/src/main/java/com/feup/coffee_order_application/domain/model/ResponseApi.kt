package com.feup.coffee_order_application.domain.model

data class ResponseApi(
    val user: User,
    val message: String,
    val success: Boolean
)