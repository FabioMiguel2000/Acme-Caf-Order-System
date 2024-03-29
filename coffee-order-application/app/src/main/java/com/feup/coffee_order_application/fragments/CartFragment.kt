package com.feup.coffee_order_application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.adapters.CartAdapter
import com.feup.coffee_order_application.databinding.FragmentCartBinding
import com.feup.coffee_order_application.models.CartProduct
import com.feup.coffee_order_application.models.Order
import com.feup.coffee_order_application.utils.FileUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.round

//val cartProducts = mutableListOf<CartProduct>(
//    CartProduct("Cappuccino", 2.5, R.drawable.cappucino, "Coffee", 12),
//    CartProduct("Mocha", 3.0, R.drawable.mocha, "Coffee", 12),
//    CartProduct("Frappuccino", 3.5, R.drawable.frappuccino, "Coffee", 12),
//    CartProduct("Oleato", 2.0, R.drawable.oleato, "Coffee", 12),
//)


class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val cartOrder by lazy { FileUtils.readOrderFromFile(requireContext()) }
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        FileUtils.saveOrderToFile(Order(cartProducts,null, null), requireContext())
        setupActionBar()
        setupRecyclerView()
        updateUI()
        setupListeners()
    }

    private fun setupRecyclerView() {
        val adapter = CartAdapter(cartOrder.cartProducts) {
            FileUtils.saveOrderToFile(cartOrder, requireContext())
            updatePrices()
            updateCartRendering(cartOrder.cartProducts.isEmpty())
        }

        binding.rvCart.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    private fun updateUI() {
        updatePrices()
        updateCartRendering(cartOrder.cartProducts.isEmpty())
        updateVoucherStatus()
    }
    private fun updateVoucherStatus() {
        binding.tvSelectCoffeeVoucher.text = getString(
            if (cartOrder.coffeeVoucher == null) R.string.voucher_not_selected else R.string.voucher_selected
        )
        binding.tvSelectDiscountVoucher.text = getString(
            if (cartOrder.discountVoucher == null) R.string.voucher_not_selected else R.string.voucher_selected
        )
    }

    private fun setupListeners() {
        binding.btnGoShopNow.setOnClickListener {
            navigateToFragment(CategoriesFragment())

            val activity = requireActivity() as AppCompatActivity
            val bottomNavigationView: BottomNavigationView = activity.findViewById(R.id.bottom_nav)
            bottomNavigationView.selectedItemId = R.id.home
        }
        binding.discountVoucherContainer.setOnClickListener { navigateToFragment(VouchersApplyFragment.newInstance("discount")) }
        binding.coffeeVoucherContainer.setOnClickListener { navigateToFragment(VouchersApplyFragment.newInstance("coffee")) }
    }

    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fLayout, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun setupActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Cart"
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
    }
    fun View.setVisible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }
    private fun updateCartRendering(isCartEmpty: Boolean) {
        binding.emptyCartContainer.setVisible(isCartEmpty)
        listOf(binding.coffeeVoucherContainer, binding.discountVoucherContainer, binding.bottomBoxContainer, binding.btnCheckout, binding.line1, binding.line2, binding.line3)
            .forEach { it.setVisible(!isCartEmpty) }
    }

    private fun updatePrices() {
        val subtotalPrice = calculateSubtotalPrice()
        val discountPrice = calculateDiscountPrice(subtotalPrice)
        val totalPrice = calculateTotalPrice(subtotalPrice, discountPrice)

        with(binding) {
            tvSubtotalPrice.text = getString(R.string.price_format, subtotalPrice)
            tvPromotionDiscount.text = getString(R.string.negative_price_format, discountPrice)
            tvTotal.text = getString(R.string.price_format, totalPrice)
        }
    }

    private fun calculateSubtotalPrice() = round(cartOrder.cartProducts.sumOf { it.price * it.quantity } * 100) / 100

    private fun calculateDiscountPrice(subtotalPrice: Double) =
        cartOrder.discountVoucher?.let { round(subtotalPrice * 0.05 * 100) / 100 } ?: 0.0

    private fun calculateTotalPrice(subtotalPrice: Double, discountPrice: Double) = subtotalPrice - discountPrice

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}