package com.feup.coffee_order_application.ui.fragment

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
import com.feup.coffee_order_application.ui.adapter.CategoriesAdapter
import com.feup.coffee_order_application.domain.model.Category
import com.feup.coffee_order_application.core.service.ServiceLocator


class HomeFragment : Fragment() {
    private val categories = mutableListOf<Category>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupActionBar()
        setupRecyclerView(view)

        ServiceLocator.productRepository.fetchProductCategories { categories ->
            categories?.let {
                this.categories.clear()
                this.categories.addAll(it)
                updateRecyclerView()
            }
        }
    }
    private fun setupRecyclerView(view: View) {
        val adapter = CategoriesAdapter(mutableListOf())
        view.findViewById<RecyclerView>(R.id.rv_categories).apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            this.adapter = adapter
        }
    }

    private fun updateRecyclerView() {
        if (isAdded) {
            val recyclerView = requireView().findViewById<RecyclerView>(R.id.rv_categories)
            (recyclerView.adapter as? CategoriesAdapter)?.let { adapter ->
                adapter.categories.clear()
                adapter.categories.addAll(this.categories)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Categories"
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
    }
}