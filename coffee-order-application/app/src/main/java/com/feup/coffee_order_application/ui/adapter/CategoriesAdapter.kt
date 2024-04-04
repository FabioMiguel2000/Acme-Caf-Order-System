package com.feup.coffee_order_application.ui.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.ui.fragment.ProductsFragment
import com.feup.coffee_order_application.domain.model.Category
import com.feup.coffee_order_application.core.utils.ImageUtils

class CategoriesAdapter(val categories: MutableList<Category>) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.img_category)
        val nameTextView: TextView = view.findViewById(R.id.tv_category_name)
        val sizeTextView: TextView = view.findViewById(R.id.tv_category_size)
        val layout: ConstraintLayout = view.findViewById(R.id.product_category_layout)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_card, parent, false)
        return CategoryViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.nameTextView.text = category.name
        holder.sizeTextView.text = "(${category.size})"

        val imageUtils = ImageUtils()
        holder.imageView.setImageBitmap(imageUtils.baseImageToBitMap(category.img))
//        general.loadBase64ImageIntoView(category.img, holder.imageView)

        holder.layout.setOnClickListener {
            val fragmentManager = (holder.layout.context as AppCompatActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val productsFragment = ProductsFragment().apply {
                arguments = Bundle().apply {
                    putString("category", category._name)
                }
            }
            fragmentTransaction.replace(R.id.fLayout, productsFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    override fun getItemCount() = categories.size
}