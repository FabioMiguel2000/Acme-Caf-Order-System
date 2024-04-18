package com.feup.coffee_order_terminal.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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
            tvClientName.text = "Name: ${order.client?.name}"
            tvClientEmail.text = "Email: ${order.client?.email}"
            tvClientNif.text = "VAT Number: ${order.client?.nif}"

            //Summary
            tvOrderCode.text = "#${order._id.toString().uppercase()}"
            tvOrderDate.text = "Purchased on ${DateFormatter().formatDate(order.date!!)}"

            //PRODUCT
            rvProducts.layoutManager = LinearLayoutManager(holder.itemView.context, RecyclerView.VERTICAL, false)
            rvProducts.adapter = ProductsAdapter(order.products)

            //Voucher
            tvOrderDVoucherCode.text = order.discountVoucher?._id.takeUnless { it.isNullOrEmpty() } ?: "Voucher not used"
            tvOrderCVoucherCode.text = order.freeCoffeeVoucher?._id.takeUnless { it.isNullOrEmpty() } ?: "Coffee voucher not used"

            //Price
            tvSubtotalPrice.text = "Subtotal: ${order.subtotal} €"
            tvPromotionDiscount.text = "Discount: ${order.promotionDiscount} €"
            tvTotal.text = "Total: ${order.total} €"
        }
    }

    override fun getItemCount() = orders.size
}