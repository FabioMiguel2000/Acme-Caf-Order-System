package com.feup.coffee_order_terminal.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_terminal.core.utils.ImageUtils
import com.feup.coffee_order_terminal.databinding.ProductCardBinding
import com.feup.coffee_order_terminal.domain.model.CartProduct
import kotlin.math.round

class ProductsAdapter(val products: MutableList<CartProduct>) :
    RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
    class ProductViewHolder(val binding: ProductCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsAdapter.ProductViewHolder {
        val binding = ProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        with(holder.binding) {
            val cartProduct = products[position]
            val product = cartProduct.product
            tvOrderQuantity.text = "Qty ${cartProduct.quantity}"
            tvOrderName.text = product.name
            tvOrderPricePerPiece.text = "${product.price} € / per piece"

            ImageUtils().loadImageFromUrlIntoView(product.imgURL, imgOrder)

            tvOrderTotalPricePerItem.text =
                "${round(product.price * cartProduct.quantity * 100) / 100} €"

            plusBtnOrder.isVisible = false
            minusBtnOrder.isVisible = false
            removeBtnOrder.isVisible = false
            line.isVisible = false

        }
    }

    override fun getItemCount() = products.size
}