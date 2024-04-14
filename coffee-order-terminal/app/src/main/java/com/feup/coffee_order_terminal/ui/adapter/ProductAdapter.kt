package com.feup.coffee_order_terminal.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_terminal.R
import com.feup.coffee_order_terminal.models.ProductOrder
import com.feup.coffee_order_terminal.core.utils.ImageUtils

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
        holder.pPreco.text = product.product.price.toString()
        holder.pQtd.text= product.quantity.toString()
        ImageUtils().loadImageFromUrlIntoView(product.product.imgURL, holder.pImage)
    }

    class ProductViewHolder(view: View): RecyclerView.ViewHolder(view){
        val pNome: TextView = view.findViewById(R.id.prod_name)
        val pPreco: TextView = view.findViewById(R.id.order_total_price_per_item)
        val pQtd: TextView = view.findViewById(R.id.prod_qtd)
        val pImage: ImageView = view.findViewById(R.id.pImage)
    }
}