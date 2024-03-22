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
import kotlin.math.round

class CartAdapter(private val products: List<CartProduct>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.img_order)
        val nameTextView: TextView = view.findViewById(R.id.tv_order_name)
        val quantityTextView: TextView = view.findViewById(R.id.tv_order_quantity)
        val priceTextView: TextView = view.findViewById(R.id.tv_order_price_per_piece)
        val totalPricePerItemTextView: TextView =
            view.findViewById(R.id.tv_order_total_price_per_item)
        val plusButton: ImageView = view.findViewById(R.id.plus_btn_order)
        val minusButton: ImageView = view.findViewById(R.id.minus_btn_order)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.order_item_card, parent, false)
        return CartViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = products[position]
        holder.quantityTextView.text = product.quantity.toString()
        holder.nameTextView.text = product.name
        holder.priceTextView.text = "${product.price} € / per piece"
        holder.imageView.setImageResource(product.imageUrl)
        holder.totalPricePerItemTextView.text =
            "${round(product.price * product.quantity * 100) / 100} €"

        holder.plusButton.setOnClickListener {
            product.quantity += 1
            holder.quantityTextView.text = product.quantity.toString()
            holder.totalPricePerItemTextView.text =
                "${round(product.price * product.quantity * 100) / 100} €"
            notifyItemChanged(position)
        }
        holder.minusButton.setOnClickListener {
            if (product.quantity > 1) {
                product.quantity -= 1
                holder.quantityTextView.text = product.quantity.toString()
                holder.totalPricePerItemTextView.text =
                    "${round(product.price * product.quantity * 100) / 100} €"
                notifyItemChanged(position)

            }
        }
    }

    override fun getItemCount() = products.size

}