package com.feup.coffee_order_application.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.databinding.ReceiptCardBinding
import com.feup.coffee_order_application.domain.model.CartProduct
import kotlin.math.round

class ReceiptListAdapter(
    private val products: MutableList<CartProduct>
) : RecyclerView.Adapter<ReceiptListAdapter.ReceiptListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptListViewHolder {
        val binding = ReceiptCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReceiptListViewHolder(binding)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ReceiptListViewHolder, position: Int) {
        val product = products[position]
    }
    override fun getItemCount() = products.size

    class ReceiptListViewHolder(val binding: ReceiptCardBinding) : RecyclerView.ViewHolder(binding.root)
}
