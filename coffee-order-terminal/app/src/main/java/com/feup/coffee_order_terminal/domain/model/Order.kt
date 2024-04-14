package com.feup.coffee_order_terminal.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Order(
    var _id: String? = null,
    var client: User? = null,
    var products: MutableList<CartProduct> = mutableListOf(),
    var freeCoffeeVoucher: Voucher? = null,
    var discountVoucher: Voucher? = null,
    val status: String = STATUS_PENDING,
    val subtotal: Double? = null,
    val promotionDiscount: Double? = null,
    val total: Double? = null,
    val date: String? = null,
): Parcelable {
    companion object {
        const val STATUS_PENDING = "Pending"
        const val STATUS_VERIFIED = "Verified"
    }
}


