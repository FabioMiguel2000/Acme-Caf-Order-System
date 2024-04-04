package com.feup.coffee_order_application.domain.model

class Order(
    var cartProducts: MutableList<CartProduct> = mutableListOf(),
    var coffeeVoucher: Voucher? = null,
    var discountVoucher: Voucher? = null,
)

