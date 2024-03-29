package com.feup.coffee_order_application.models

data class Voucher(
    val uuid: String,
    val type: String,
    val clientId: String,
    val used: Boolean,
    var isSelected: Boolean
)
