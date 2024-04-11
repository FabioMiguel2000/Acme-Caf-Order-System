package com.feup.coffee_order_terminal.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_terminal.R
import com.feup.coffee_order_terminal.models.ProductOrder

class ProductAdapter (val products: MutableList<ProductOrder>): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.pNome.text = product.product.name
        holder.pCategory.text= product.product.category.name
        holder.pQtd.text= product.quantity
    }

    class ProductViewHolder(view: View): RecyclerView.ViewHolder(view){
        val pNome: TextView = view.findViewById(R.id.prod_name)
        val pCategory: TextView = view.findViewById(R.id.prod_category)
        val pQtd: TextView = view.findViewById(R.id.prod_qtd)
    }
}