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
class ProductsAdapter(private val products: List<CartProduct>) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView: ImageView = view.findViewById(R.id.img_product)
        val nameTextView: TextView = view.findViewById(R.id.tv_product_name)
        val btnAdd: ImageView = view.findViewById(R.id.btn_add_to_cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_card, parent, false)
        return ProductsViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = products[position]
        holder.nameTextView.text = product.name
        holder.imageView.setImageResource(product.imageUrl)

        holder.btnAdd.setOnClickListener {

        }
    }
    override fun getItemCount() = products.size
}