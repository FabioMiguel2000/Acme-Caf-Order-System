package com.feup.coffee_order_application.ui.fragment

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.toUpperCase
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.core.service.SessionManager
import com.feup.coffee_order_application.core.utils.OrderStorageUtils
import com.feup.coffee_order_application.core.utils.QRCodeGenerator
import com.feup.coffee_order_application.databinding.FragmentCheckoutBinding
import com.feup.coffee_order_application.domain.model.Order
import com.feup.coffee_order_application.ui.adapter.CheckoutAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlin.math.round

interface OnBackPressedInCheckout {
    fun onBackPressed(): Boolean
}

class CheckoutFragment: Fragment(), OnBackPressedInCheckout{
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
        setNavBarVisibility(View.GONE)
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            })
        }
    }
    private fun setNavBarVisibility(visibility: Int){
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.visibility = visibility
    }
    private fun setupRecyclerView() {
        val adapter = CheckoutAdapter(cartOrder.cartProducts)

        binding.rvCheckoutOrder.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    private fun updateUI() {
        displayQRCode()
        binding.tvCheckoutCVoucherCode.text = cartOrder.coffeeVoucher?._id?.toUpperCase()?: "No Voucher"
        binding.tvCheckoutDVoucherCode.text = cartOrder.discountVoucher?._id?.toUpperCase()?: "No Voucher"
        updatePrices()
    }

    private fun displayQRCode() {
        cartOrder.client = SessionManager(requireContext()).fetchUserToken()!!
        val orderJson = Gson().toJson(cartOrder)
        val qrCodeBitmap = QRCodeGenerator().generateQRCode(orderJson)

        qrCodeBitmap?.let {
            binding.ivQrCode.setImageBitmap(it)
        }
    }

    private fun setupActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Checkout"
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

    private fun calculateSubtotalPrice() = round(cartOrder.cartProducts.sumOf { it.product.price * it.quantity } * 100) / 100

    private fun calculateDiscountPrice(subtotalPrice: Double) =
        cartOrder.discountVoucher?.let { round(subtotalPrice * 0.05 * 100) / 100 } ?: 0.0

    private fun calculateTotalPrice(subtotalPrice: Double, discountPrice: Double) = subtotalPrice - discountPrice

    override fun onBackPressed(): Boolean {
        showExitConfirmationDialog()
        return true
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm Exit")
            .setMessage("Are you sure you want to exit the checkout process?\n\nIf you exit, your order will be lost.")
            .setPositiveButton("Yes") { dialog, which ->
                requireActivity().supportFragmentManager.popBackStack()
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        OrderStorageUtils.clearOrderFile(requireContext())

        setNavBarVisibility(View.VISIBLE)
    }
}