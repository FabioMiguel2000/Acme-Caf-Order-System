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
import com.feup.coffee_order_application.databinding.FragmentCartBinding
import com.feup.coffee_order_application.models.CartProduct
import com.feup.coffee_order_application.models.Category
import com.feup.coffee_order_application.models.Order
import com.feup.coffee_order_application.models.Voucher
import com.feup.coffee_order_application.utils.FileUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import kotlin.math.round

//val cartProducts = mutableListOf<CartProduct>(
//    CartProduct("Cappuccino", 2.5, R.drawable.cappucino, "Coffee", 12),
//    CartProduct("Mocha", 3.0, R.drawable.mocha, "Coffee", 12),
//    CartProduct("Frappuccino", 3.5, R.drawable.frappuccino, "Coffee", 12),
//    CartProduct("Oleato", 2.0, R.drawable.oleato, "Coffee", 12),
//)

class Cart : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private lateinit var cartOrder: Order
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Order"

        val actionBar = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setDisplayShowHomeEnabled(false)

        cartOrder = FileUtils.readOrderFromFile(requireContext())
//        FileUtils.saveOrderToFile(Order(cartProducts, null, null), requireContext())
        val adapter = CartAdapter(cartOrder.cartProducts)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_cart)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        updatePrices()
        updateCartRendering(cartOrder.cartProducts.isEmpty())

        binding.tvSelectCoffeeVoucher.text = if (cartOrder.coffeeVoucher == null) "Not Selected" else "Selected"
        binding.tvSelectDiscountVoucher.text = if (cartOrder.discountVoucher == null) "Not Selected" else "Selected"

        binding.btnGoShopNow.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val vouchersApplyFragment = VouchersApply() // Replace HomeFragment() with your home page fragment's constructor

            fragmentTransaction.replace(R.id.fLayout, vouchersApplyFragment)
            fragmentTransaction.addToBackStack(null) // Add this transaction to the back stack (optional)
            fragmentTransaction.commit()

            val activity = requireActivity() as AppCompatActivity
            val bottomNavigationView: BottomNavigationView = activity.findViewById(R.id.bottom_nav)
            bottomNavigationView.selectedItemId = R.id.home
        }

        binding.discountVoucherContainer.setOnClickListener {
            Log.d(TAG, "Discount Voucher Clicked")
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val vouchersApplyFragment = VouchersApply() // Replace HomeFragment() with your home page fragment's constructor

            fragmentTransaction.replace(R.id.fLayout, vouchersApplyFragment)
            fragmentTransaction.addToBackStack(null) // Add this transaction to the back stack (optional)
            fragmentTransaction.commit()

        }

        binding.coffeeVoucherContainer.setOnClickListener {
            Log.d(TAG, "Discount Voucher Clicked")
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val categoriesFragment = VouchersApply() // Replace HomeFragment() with your home page fragment's constructor

            fragmentTransaction.replace(R.id.fLayout, categoriesFragment)
            fragmentTransaction.addToBackStack(null) // Add this transaction to the back stack (optional)
            fragmentTransaction.commit()

        }

        adapter.setCartQuantityChangeListener(object : CartQuantityChangeListener {
            override fun onQuantityChanged() {
                FileUtils.saveOrderToFile(cartOrder, requireContext())
                updatePrices()
                updateCartRendering(cartOrder.cartProducts.isEmpty())
            }
        })
    }

    private fun updateCartRendering(isCartEmpty: Boolean) {
        with(binding){
            emptyCartContainer.visibility = if (isCartEmpty) View.VISIBLE else View.GONE
            coffeeVoucherContainer.visibility = if (isCartEmpty) View.GONE else View.VISIBLE
            discountVoucherContainer.visibility = if (isCartEmpty) View.GONE else View.VISIBLE
            bottomBoxContainer.visibility = if (isCartEmpty) View.GONE else View.VISIBLE
            btnCheckout.visibility = if (isCartEmpty) View.GONE else View.VISIBLE
            line1.visibility = if (isCartEmpty) View.GONE else View.VISIBLE
            line2.visibility = if (isCartEmpty) View.GONE else View.VISIBLE
            line3.visibility = if (isCartEmpty) View.GONE else View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updatePrices() {
        val subtotalPrice = round(cartOrder.cartProducts.sumOf { it.price * it.quantity } * 100) / 100
        with(binding){
            tvSubtotalPrice.text = "$subtotalPrice €"
            tvPromotionDiscount.text = "- 0.00 €"
            tvTotal.text = "$subtotalPrice €"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // To avoid memory leaks
    }

}