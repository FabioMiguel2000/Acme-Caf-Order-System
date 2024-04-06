package com.feup.coffee_order_application.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.toUpperCase
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.core.utils.DateFormatter
import com.feup.coffee_order_application.databinding.FragmentReceiptBinding
import com.feup.coffee_order_application.domain.model.Order
import com.feup.coffee_order_application.ui.adapter.CheckoutAdapter
import kotlin.math.round

class ReceiptFragment(val order: Order): Fragment() {
    private var _binding: FragmentReceiptBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()
        setupRecyclerView()
        updateUI()
    }

    private fun setupRecyclerView() {
        val adapter = CheckoutAdapter(order.products)

        binding.rvOrder.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI() {
        binding.let{
            it.tvOrderCode.text = "#${order._id!!.toUpperCase()}"
            it.tvOrderDate.text = DateFormatter().formatDate(order.date!!)
            it.tvOrderTotalPrice.text = " - ${order.total} €"
            it.tvOrderCVoucherCode.text = order.freeCoffeeVoucher?._id?.toUpperCase()?: "No Voucher"
            it.tvOrderDVoucherCode.text = order.discountVoucher?._id?.toUpperCase()?: "No Voucher"
        }
        updatePrices()
    }


    private fun setupActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Receipt"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
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

    private fun calculateSubtotalPrice() = round(order.products.sumOf { it.product.price * it.quantity } * 100) / 100

    private fun calculateDiscountPrice(subtotalPrice: Double) =
        order.discountVoucher?.let { round(subtotalPrice * 0.05 * 100) / 100 } ?: 0.0

    private fun calculateTotalPrice(subtotalPrice: Double, discountPrice: Double) = subtotalPrice - discountPrice

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}