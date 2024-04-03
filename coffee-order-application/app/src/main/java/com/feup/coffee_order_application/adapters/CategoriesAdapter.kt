package com.feup.coffee_order_application.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.fragments.ProductsFragment
import com.feup.coffee_order_application.models.Category
import com.feup.coffee_order_application.services.General

class CategoriesAdapter(private val categories: MutableList<Category>) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
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

        val general = General()
        holder.imageView.setImageBitmap(general.baseImageToBitMap(category.img))
//        general.loadBase64ImageIntoView(category.img, holder.imageView)

        holder.layout.setOnClickListener {
            val fragmentManager = (holder.layout.context as AppCompatActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val productsFragment = ProductsFragment().apply {
                arguments = Bundle().apply {
                    putString("category", category.name)
                }
            }
            fragmentTransaction.replace(R.id.fLayout, productsFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    override fun getItemCount() = categories.size
}