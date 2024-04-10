package com.feup.coffee_order_terminal.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_terminal.core.utils.ImageUtils
import com.feup.coffee_order_terminal.databinding.ProductItemBinding
import com.feup.coffee_order_terminal.domain.model.CartProduct

class ProductsAdapter(val products: MutableList<CartProduct>) :
    RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
    class ProductViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsAdapter.ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        with(holder.binding) {
            val cardProduct = products[position]
            tvProductName.text = cardProduct.product.name
            tvProductPricePerPiece.text = "${cardProduct.product.price} € / per piece"
            tvProductQuantity.text = "Quantity: ${cardProduct.quantity.toString()}"
            tvProductTotalPrice.text = "Total: ${cardProduct.quantity * cardProduct.product.price} €"


            ImageUtils().loadImageFromUrlIntoView(cardProduct.product.imgURL, imgProduct)
        }
    }

    override fun getItemCount() = products.size
}