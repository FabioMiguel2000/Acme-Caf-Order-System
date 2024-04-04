package com.feup.coffee_order_application.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.core.utils.ImageUtils
import com.feup.coffee_order_application.domain.model.CartProduct
import com.feup.coffee_order_application.core.utils.OrderStorageUtils
import com.feup.coffee_order_application.domain.model.Product
import com.google.android.material.button.MaterialButton

class ProductsAdapter(private val context: Context, private val products: List<Product>) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {
    private var cartOrder = OrderStorageUtils.readOrderFromFile(context)
    class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView: ImageView = view.findViewById(R.id.img_product)
        val nameTextView: TextView = view.findViewById(R.id.tv_product_name)
        val btnAdd: MaterialButton = view.findViewById(R.id.btn_add_to_cart)
        val priceTextView: TextView = view.findViewById(R.id.tv_product_price_per_piece)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        return ProductsViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = products[position]
        holder.nameTextView.text = product.name
        holder.priceTextView.text = "${product.price} € / per piece"

        ImageUtils().loadImageFromUrlIntoView(product.imgURL, holder.imageView)

        holder.btnAdd.setOnClickListener {
            val existingProduct = cartOrder.cartProducts.find { it.name == product.name }
            if (existingProduct != null) {
                existingProduct.quantity++
            } else {
                Log.d("ProductsAdapter", "Product added to cart: ${product.name}")
//                cartOrder.cartProducts.add(product)
            }
            Toast.makeText(context, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
            OrderStorageUtils.saveOrderToFile(cartOrder, context)
        }
    }
    override fun getItemCount() = products.size
}