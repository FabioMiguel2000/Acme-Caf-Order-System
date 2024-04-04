package com.feup.coffee_order_application.domain.model

data class OrderRequest(
    val client: String,
    val products: List<ProductItem>,
    val discountVoucher: String?,
    val freeCoffeeVoucher: String?,
    val status: String = OrderRequest.STATUS_PENDING
){
    companion object {
        const val STATUS_PENDING = "Pending"
        const val STATUS_VERIFIED = "Verified"
    }
}

data class ProductItem(
    val product: String,
    val quantity: Int
)