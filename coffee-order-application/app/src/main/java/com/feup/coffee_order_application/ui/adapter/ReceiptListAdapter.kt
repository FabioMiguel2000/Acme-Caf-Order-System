package com.feup.coffee_order_application.ui.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.core.utils.DateFormatter
import com.feup.coffee_order_application.databinding.ReceiptCardBinding
import com.feup.coffee_order_application.domain.model.Order
import com.feup.coffee_order_application.ui.fragment.ProductsFragment
import com.feup.coffee_order_application.ui.fragment.ReceiptFragment
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

        holder.binding.receiptCard.setOnClickListener {
            val fragmentManager = (holder.itemView.context as AppCompatActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val receiptFragment = ReceiptFragment(receipt)
            fragmentTransaction.replace(R.id.fLayout, receiptFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
    override fun getItemCount() = receipts.size

    class ReceiptListViewHolder(val binding: ReceiptCardBinding) : RecyclerView.ViewHolder(binding.root)
}
