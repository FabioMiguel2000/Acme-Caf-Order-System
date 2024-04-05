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

class CartAdapter(
    private val products: MutableList<CartProduct>,
    private val onQuantityChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = OrderItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        with(holder.binding) {
            val cartProduct = products[position]
            val product = cartProduct.product
            tvOrderQuantity.text = cartProduct.quantity.toString()
            tvOrderName.text = product.name
            tvOrderPricePerPiece.text = "${product.price} € / per piece"

            // Assuming ImageUtils is updated to handle loading images from URLs
            ImageUtils().loadImageFromUrlIntoView(product.imgURL, imgOrder)

            tvOrderTotalPricePerItem.text = "${round(product.price * cartProduct.quantity * 100) / 100} €"

            val isFreeCoffee = product.name == "Free Coffee"
            plusBtnOrder.isVisible = !isFreeCoffee
            minusBtnOrder.isVisible = !isFreeCoffee
            removeBtnOrder.isVisible = !isFreeCoffee

            setClickListener(plusBtnOrder) { increaseQuantity(cartProduct, position) }
            setClickListener(minusBtnOrder) { decreaseQuantity(cartProduct, position) }
            setClickListener(removeBtnOrder) { removeProduct(position) }
        }
    }

    override fun getItemCount() = products.size

    private fun setClickListener(view: View, clickAction: () -> Unit) {
        view.setOnClickListener { clickAction() }
    }

    private fun increaseQuantity(product: CartProduct, position: Int) {
        product.quantity++
        notifyItemChanged(position)
        onQuantityChanged()
    }

    private fun decreaseQuantity(product: CartProduct, position: Int) {
        if (product.quantity > 1) {
            product.quantity--
            notifyItemChanged(position)
            onQuantityChanged()
        }
    }

    private fun removeProduct(position: Int) {
        products.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
        onQuantityChanged()
    }

    class CartViewHolder(val binding: OrderItemCardBinding) : RecyclerView.ViewHolder(binding.root)
}
