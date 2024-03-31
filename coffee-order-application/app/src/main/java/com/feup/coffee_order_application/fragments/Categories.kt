package com.feup.coffee_order_application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.adapters.CategoriesAdapter
import com.feup.coffee_order_application.models.Category
import com.feup.coffee_order_application.services.CategoriesHolder

val categories = listOf<Category>(
    Category("Hot Coffee", R.drawable.hot_coffee, 10),
    Category("Cold Coffee", R.drawable.ice_coffee, 12),
    Category("Cappucino", R.drawable.cappucino, 8),
    Category("Frappucino", R.drawable.frappuccino, 10),
    Category("Mocha", R.drawable.mocha, 12),
    Category("Oleato", R.drawable.oleato, 8)
)
class Categories : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Categories"

        var categoriesHolder = CategoriesHolder.getInstance()

        val adapter = CategoriesAdapter(categoriesHolder.returnCategories())

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_categories)
        recyclerView.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }


}