package com.feup.coffee_order_application.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.models.CartProduct

class CartAdapter(private val products: List<CartProduct>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.img_order)
        val nameTextView: TextView = view.findViewById(R.id.tv_order_name)
        val quantityTextView: TextView = view.findViewById(R.id.tv_order_quantity)
        val priceTextView: TextView = view.findViewById(R.id.tv_order_price_per_piece)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item_card, parent, false)
        return CartViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = products[position]
        holder.quantityTextView.text = product.quantity.toString()
        holder.nameTextView.text = product.name
        holder.priceTextView.text = "${product.price} € / per piece"
        holder.imageView.setImageResource(product.imageUrl)
    }

    override fun getItemCount() = products.size

}