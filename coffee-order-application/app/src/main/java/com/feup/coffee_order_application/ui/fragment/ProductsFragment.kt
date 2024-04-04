package com.feup.coffee_order_application.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.core.service.ServiceLocator
import com.feup.coffee_order_application.ui.adapter.ProductsAdapter
import com.feup.coffee_order_application.domain.model.CartProduct
import com.feup.coffee_order_application.domain.model.Product

class ProductsFragment : Fragment() {
    private var category: String? = null
    private val products = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getString("category", "default_category")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = category?.toDisplayFormat()
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        category?.let { safeCategory ->
            ServiceLocator.productRepository.fetchProductsByCategory(safeCategory) { fetchedProducts ->
                fetchedProducts?.let {
                    products.clear()
                    products.addAll(it)
                    updateRecyclerView(view, products)
                }
            }
        }
        updateRecyclerView(view, products)
    }

    private fun updateRecyclerView(view: View, products: List<Product>) {
        val adapter = ProductsAdapter(requireContext(), products)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_products)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    fun String.toDisplayFormat(): String {
        return this.split("_") // Split the string by underscores
            .joinToString(" ") { it.capitalize() } // Capitalize each word and join with a space
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } // Ensure the first character is capitalized
    }

}