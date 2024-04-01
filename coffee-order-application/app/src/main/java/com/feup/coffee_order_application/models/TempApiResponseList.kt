package com.feup.coffee_order_application.models

data class TempApiResponseList<T>(
    val success: Boolean,
    val message: String,
    val data: List<T>     //TODO: change to List<T> when all api responses are lists
)
