package com.feup.coffee_order_application.domain.model

data class OrderRequest(
    val client: String,
    val products: List<ProductItem>,
    val discountVoucher: String?,
    val freeCoffeeVoucher: String?,
)

data class ProductItem(
    val product: String,
    val quantity: Int
)