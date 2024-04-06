package com.feup.coffee_order_application.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.core.utils.DateFormatter
import com.feup.coffee_order_application.databinding.ReceiptCardBinding
import com.feup.coffee_order_application.domain.model.Order
import kotlin.math.round

class ReceiptListAdapter(
    val receipts: MutableList<Order>
) : RecyclerView.Adapter<ReceiptListAdapter.ReceiptListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptListViewHolder {
        val binding = ReceiptCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReceiptListViewHolder(binding)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ReceiptListViewHolder, position: Int) {
        val receipt = receipts[position]

        val totalProducts = receipt.products.sumOf { it.quantity }

        holder.binding.tvReceiptProductSize.text = "$totalProducts products"
        holder.binding.tvReceiptTotalPrice.text = "${round(receipt.total!! * 100) / 100} €"
        holder.binding.tvReceiptDate.text = DateFormatter().formatDate(receipt.date!!)
    }
    override fun getItemCount() = receipts.size

    class ReceiptListViewHolder(val binding: ReceiptCardBinding) : RecyclerView.ViewHolder(binding.root)
}
