package com.feup.coffee_order_application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.adapters.CategoriesAdapter
import com.feup.coffee_order_application.models.Category


class Categories : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categories = listOf<Category>(
            Category("Hot Coffee", R.drawable.hot_coffee, 10),
            Category("Cold Coffee", R.drawable.ice_coffee, 12),
            Category("Cappucino", R.drawable.cappuccino, 8)
        )

        val adapter = CategoriesAdapter(categories)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_categories)
        recyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.categories_fragment, container, false)
    }

}