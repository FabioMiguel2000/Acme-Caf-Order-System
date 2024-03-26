package com.feup.coffee_order_application.models

class Order(
    var cartProducts: MutableList<CartProduct> = mutableListOf(),
    val coffeeVoucher: Voucher? = null,
    val discountVoucher: Voucher? = null,
)

