package com.feup.coffee_order_application.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.adapters.CategoriesAdapter
import com.feup.coffee_order_application.models.CategoryItem
import com.feup.coffee_order_application.services.ServiceLocator

class Categories : Fragment() {
    private val categories = mutableListOf<CategoryItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    private fun setupRecyclerView(view: View) {
        val adapter = CategoriesAdapter(categories)
        Log.d("categories", categories.size.toString())

        view.findViewById<RecyclerView>(R.id.rv_categories).apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            this.adapter = adapter
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Categories"

        ServiceLocator.productRepository.fetchProductCategories { categories ->
            categories?.let {
                this.categories.clear()
                this.categories.addAll(it)
                setupRecyclerView(view)
            }
        }
    }
}