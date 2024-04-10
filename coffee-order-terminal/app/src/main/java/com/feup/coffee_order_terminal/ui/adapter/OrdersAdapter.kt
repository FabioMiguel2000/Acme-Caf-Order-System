package com.feup.coffee_order_terminal.ui.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_terminal.core.utils.DateFormatter
import com.feup.coffee_order_terminal.databinding.OrderItemCardBinding
import com.feup.coffee_order_terminal.domain.model.Order

class OrdersAdapter(val orders: MutableList<Order>) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {
    class OrderViewHolder(val binding: OrderItemCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        with(holder.binding) {
            val order = orders[position]
            val product = order.products

            //Client
            tvClientName.text = order.client?.name

            //Summary
            tvOrderCode.text = order._id
            tvOrderDate.text = DateFormatter().formatDate(order.date!!)

            //PRODUCT
            //tvOrderQuantity.text = "Qty ${product.quantity}"
            //tvOrderName.text = product.name
            //tvOrderPricePerPiece.text = "${product.price} € / per piece"
            //ImageUtils().loadImageFromUrlIntoView(product.imgURL, imgOrder)


            //Voucher
            tvOrderDVoucherCode.text = order.discountVoucher?._id
            tvOrderCVoucherCode.text = order.freeCoffeeVoucher?._id

            //Price
            tvSubtotalPrice.text = "${order.subtotal} €"
            tvPromotionDiscount.text = "${order.promotionDiscount} €"
            tvTotal.text = "${order.total} €"
        }
    }

    override fun getItemCount() = orders.size
}