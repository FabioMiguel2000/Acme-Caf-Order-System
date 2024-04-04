package com.feup.coffee_order_application.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.core.utils.ImageUtils
import com.feup.coffee_order_application.databinding.OrderItemCardBinding
import com.feup.coffee_order_application.domain.model.CartProduct
import kotlin.math.round

class CheckoutAdapter(
    private val products: MutableList<CartProduct>
) : RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
        val binding = OrderItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CheckoutViewHolder(binding)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
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

    class CheckoutViewHolder(val binding: OrderItemCardBinding) : RecyclerView.ViewHolder(binding.root)
}
