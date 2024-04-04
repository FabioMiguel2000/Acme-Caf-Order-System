package com.feup.coffee_order_application.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.core.utils.OrderStorageUtils
import com.feup.coffee_order_application.databinding.FragmentCheckoutBinding
import com.feup.coffee_order_application.domain.model.Order
import com.feup.coffee_order_application.ui.adapter.CheckoutAdapter
import kotlin.math.round

class CheckoutFragment: Fragment() {
    private var _binding: FragmentCheckoutBinding? = null
    private lateinit var cartOrder: Order
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartOrder = OrderStorageUtils.readOrderFromFile(requireContext())
        setupActionBar()
        setupRecyclerView()
        updateUI()
    }
    private fun setupRecyclerView() {
        val adapter = CheckoutAdapter(cartOrder.cartProducts)

        binding.rvCheckoutOrder.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    private fun updateUI() {
        updatePrices()
    }

    private fun setupActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Checkout"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }
    fun View.setVisible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
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

    private fun calculateSubtotalPrice() = round(cartOrder.cartProducts.sumOf { it.product.price * it.quantity } * 100) / 100

    private fun calculateDiscountPrice(subtotalPrice: Double) =
        cartOrder.discountVoucher?.let { round(subtotalPrice * 0.05 * 100) / 100 } ?: 0.0

    private fun calculateTotalPrice(subtotalPrice: Double, discountPrice: Double) = subtotalPrice - discountPrice

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}