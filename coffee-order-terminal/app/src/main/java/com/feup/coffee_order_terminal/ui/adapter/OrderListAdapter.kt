package com.feup.coffee_order_terminal.ui.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_terminal.R
import com.feup.coffee_order_terminal.core.utils.DateFormatter
import com.feup.coffee_order_terminal.databinding.OrderCardBinding
import com.feup.coffee_order_terminal.domain.model.Order
import com.feup.coffee_order_terminal.ui.fragment.OrderFragment
import kotlin.math.round

class OrderListAdapter(
    val orders: MutableList<Order>
) : RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val binding = OrderCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderListViewHolder(binding)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        val order = orders[position]

        val totalProducts = order.products.sumOf { it.quantity }

        holder.binding.tvReceiptProductSize.text = "$totalProducts products"
        holder.binding.tvReceiptTotalPrice.text = "${round(order.total!! * 100) / 100} €"
        holder.binding.tvReceiptDate.text = DateFormatter().formatDate(order.date!!)

        holder.binding.receiptCard.setOnClickListener {
            val fragmentManager = (holder.itemView.context as AppCompatActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val orderFragment = OrderFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("order", order) // Ensure that Order class implements Parcelable
                }
            }
            fragmentTransaction.replace(R.id.fLayout, orderFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
    override fun getItemCount() = orders.size

    class OrderListViewHolder(val binding: OrderCardBinding) : RecyclerView.ViewHolder(binding.root)
}