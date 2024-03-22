package com.feup.coffee_order_application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.adapters.CartAdapter
import com.feup.coffee_order_application.adapters.CategoriesAdapter
import com.feup.coffee_order_application.models.CartProduct
import com.feup.coffee_order_application.models.Category

val cartProducts = listOf<CartProduct>(
    CartProduct("Hot Coffee", 1.99, R.drawable.hot_coffee, "", 1),
    CartProduct("Cold Coffee", 2.99, R.drawable.ice_coffee, "",12),

)

class Cart : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Order"

        val adapter = CartAdapter(cartProducts)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_cart)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

}