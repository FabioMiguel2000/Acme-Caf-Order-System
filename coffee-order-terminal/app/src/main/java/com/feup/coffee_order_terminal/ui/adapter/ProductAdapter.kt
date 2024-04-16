package com.feup.coffee_order_terminal.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_terminal.R
import com.feup.coffee_order_terminal.core.utils.ImageUtils
import com.feup.coffee_order_terminal.databinding.ProductItemBinding
import com.feup.coffee_order_terminal.domain.model.CartProduct
import kotlin.math.round

class ProductAdapter(
    val products: MutableList<CartProduct>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

            tvOrderTotalPricePerItem.text = "${round(product.price * cartProduct.quantity * 100) / 100} €"

            plusBtnOrder.isVisible = false
            minusBtnOrder.isVisible = false
            removeBtnOrder.isVisible = false
            line.isVisible = false

        }
    }
    override fun getItemCount() = products.size

    class ProductViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)
}
