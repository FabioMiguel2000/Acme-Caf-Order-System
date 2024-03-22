package com.feup.coffee_order_application.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.adapters.CartAdapter
import com.feup.coffee_order_application.adapters.CartQuantityChangeListener
import com.feup.coffee_order_application.adapters.CategoriesAdapter
import com.feup.coffee_order_application.models.CartProduct
import com.feup.coffee_order_application.models.Category
import kotlin.math.round

val cartProducts = listOf<CartProduct>(
    CartProduct("Hot Coffee", 1.99, R.drawable.hot_coffee, "", 1),
    CartProduct("Cold Coffee", 2.99, R.drawable.ice_coffee, "", 12),
    CartProduct("Coffee", 1.99, R.drawable.hot_coffee, "", 1),
    CartProduct("Coffee 2", 2.99, R.drawable.ice_coffee, "", 2),
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Order"

        val adapter = CartAdapter(cartProducts)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_cart)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        val subtotalTextView: TextView = view.findViewById(R.id.tv_subtotal_price)
        val promotionDiscountTextView: TextView = view.findViewById(R.id.tv_promotion_discount)
        val totalTextView: TextView = view.findViewById(R.id.tv_total)

        val subtotalPrice = round(cartProducts.sumOf { it.price * it.quantity } * 100) / 100

        subtotalTextView.text = "$subtotalPrice €"
        promotionDiscountTextView.text = "- 0.00 €"
        totalTextView.text = "$subtotalPrice €"

        adapter.setCartQuantityChangeListener(object : CartQuantityChangeListener {
            override fun onQuantityChanged() {
                val subtotalPrice = round(cartProducts.sumOf { it.price * it.quantity } * 100) / 100

                subtotalTextView.text = "$subtotalPrice €"
                promotionDiscountTextView.text = "- 0.00 €"
                totalTextView.text = "$subtotalPrice €"

            }
        })
    }


}