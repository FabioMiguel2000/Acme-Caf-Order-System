package com.feup.coffee_order_application.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.models.Category
import com.feup.coffee_order_application.models.CategoryItem
import com.feup.coffee_order_application.services.General
import java.util.LinkedList

class CategoriesAdapter(private val categories: LinkedList<CategoryItem>) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.img_category)
        val nameTextView: TextView = view.findViewById(R.id.tv_category_name)
        val sizeTextView: TextView = view.findViewById(R.id.tv_category_size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_card, parent, false)
        return CategoryViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val general = General()
        val category = categories[position]
        holder.nameTextView.text = category.name
        //holder.sizeTextView.text = "(${category.size})"
        holder.sizeTextView.text = "4"
        //holder.imageView.setImageResource(category.imageSrc)
        holder.imageView.setImageBitmap(general.baseImageToBitMap(category.img))
    }

    override fun getItemCount() = categories.size
}