package com.feup.coffee_order_application.fragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.adapters.CartAdapter
import com.feup.coffee_order_application.adapters.CartQuantityChangeListener
import com.feup.coffee_order_application.adapters.CategoriesAdapter
import com.feup.coffee_order_application.models.CartProduct
import com.feup.coffee_order_application.models.Category
import com.google.android.material.button.MaterialButton
import kotlin.math.round

val cartProducts = mutableListOf<CartProduct>(
    CartProduct("Hot Coffee", 1.99, R.drawable.hot_coffee, "", 1),
    CartProduct("Cold Coffee", 2.99, R.drawable.ice_coffee, "", 12),
    CartProduct("Coffee", 1.99, R.drawable.hot_coffee, "", 1),
    CartProduct("Coffee 2", 2.99, R.drawable.ice_coffee, "", 2),
)

//val cartProducts = mutableListOf<CartProduct>()
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

        updateCartRendering(view, cartProducts.isEmpty())

        adapter.setCartQuantityChangeListener(object : CartQuantityChangeListener {
            override fun onQuantityChanged() {
                Log.d(TAG, "Quantity changed")
                val subtotalPrice = round(cartProducts.sumOf { it.price * it.quantity } * 100) / 100

                subtotalTextView.text = "$subtotalPrice €"
                promotionDiscountTextView.text = "- 0.00 €"
                totalTextView.text = "$subtotalPrice €"

                updateCartRendering(view, cartProducts.isEmpty())
            }
        })

    }

    fun updateCartRendering(view:View , isCartEmpty: Boolean) {
        view.findViewById<ConstraintLayout>(R.id.empty_cart_container).visibility =
            if (isCartEmpty) View.VISIBLE else View.GONE
        view.findViewById<ConstraintLayout>(R.id.coffee_voucher_container).visibility =
            if (isCartEmpty) View.GONE else View.VISIBLE
        view.findViewById<ConstraintLayout>(R.id.discount_voucher_container).visibility =
            if (isCartEmpty) View.GONE else View.VISIBLE
        view.findViewById<ConstraintLayout>(R.id.bottom_box_container).visibility =
            if (isCartEmpty) View.GONE else View.VISIBLE
        view.findViewById<MaterialButton>(R.id.btn_checkout).visibility =
            if (isCartEmpty) View.GONE else View.VISIBLE
        view.findViewById<View>(R.id.line_1).visibility =
            if (isCartEmpty) View.GONE else View.VISIBLE
        view.findViewById<View>(R.id.line_2).visibility =
            if (isCartEmpty) View.GONE else View.VISIBLE
        view.findViewById<View>(R.id.line_3).visibility =
            if (isCartEmpty) View.GONE else View.VISIBLE
    }


}