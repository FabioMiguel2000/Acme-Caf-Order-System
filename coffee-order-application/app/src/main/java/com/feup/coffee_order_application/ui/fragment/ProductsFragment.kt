package com.feup.coffee_order_application.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.ui.adapter.ProductsAdapter
import com.feup.coffee_order_application.domain.model.CartProduct

val products = mutableListOf<CartProduct>(
    CartProduct("Hot Coffee", 1.99, R.drawable.hot_coffee, "", 1),
    CartProduct("Cold Coffee", 2.99, R.drawable.ice_coffee, "", 1),
    CartProduct("Coffee", 1.99, R.drawable.hot_coffee, "", 1),
    CartProduct("Coffee 2", 2.99, R.drawable.ice_coffee, "", 1),
)

class ProductsFragment : Fragment() {
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
            title = "Hot Coffee"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val adapter = ProductsAdapter(requireContext(), products)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_products)
        recyclerView.layoutManager =
            LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}