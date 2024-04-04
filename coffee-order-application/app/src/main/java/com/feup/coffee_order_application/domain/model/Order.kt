package com.feup.coffee_order_application.domain.model

class Order(
    var client: String? = null,
    var cartProducts: MutableList<CartProduct> = mutableListOf(),
    var coffeeVoucher: Voucher? = null,
    var discountVoucher: Voucher? = null,
    val status: String = STATUS_PENDING
){
    companion object {
        const val STATUS_PENDING = "Pending"
        const val STATUS_VERIFIED = "Verified"
    }
}


