package com.feup.coffee_order_application.models

data class ProductCategory(
    val `data`: List<CategoryItem>,
    val message: String,
    val success: Boolean
)