package com.feup.coffee_order_application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.adapters.VoucherAdapter
import com.feup.coffee_order_application.models.CartProduct
import com.feup.coffee_order_application.models.Voucher
import com.feup.coffee_order_application.services.ServiceLocator
import com.feup.coffee_order_application.utils.FileUtils
import com.google.android.material.button.MaterialButton

class VouchersApplyFragment : Fragment() {
    private val cartOrder by lazy { FileUtils.readOrderFromFile(requireContext()) }
    private var userId: String = "31ca6621550a71fdb4629390d1d264a2" // hardcoded user id, TODO: get from shared preferences (session)
    private val vouchers = mutableListOf<Voucher>()
    private var voucherType: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            voucherType = it.getString(ARG_VOUCHER_TYPE)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vouchers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchVouchers()
        setupActionBar()
    }

    private fun fetchVouchers(){
        ServiceLocator.userRepository.getUserVouchers(userId) { vouchers ->
            vouchers?.let {
                this.vouchers.addAll(it)
            }
            setupRecyclerView(requireView())
            markSelectedVouchers()
            setupApplyVoucherButton(requireView())
        }
    }


    private fun setupRecyclerView(view: View) {
        val filteredVouchers = vouchers.filter { it.type == voucherType }.toMutableList()
        val adapter = VoucherAdapter(filteredVouchers)
        view.findViewById<RecyclerView>(R.id.rv_vouchers).apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    private fun markSelectedVouchers() {
        cartOrder.discountVoucher?.let { discountVoucher ->
            vouchers.find { it._id == discountVoucher._id }?.isSelected = true
        }
        cartOrder.coffeeVoucher?.let { coffeeVoucher ->
            vouchers.find { it._id == coffeeVoucher._id }?.isSelected = true
        }
    }

    private fun setupApplyVoucherButton(view: View) {
        view.findViewById<MaterialButton>(R.id.btn_apply_voucher).setOnClickListener {
            applySelectedVoucher()
            navigateToCartFragment()
        }
    }

    private fun applySelectedVoucher() {
        vouchers.firstOrNull { it.isSelected && it.type == voucherType }?.let { selectedVoucher ->
            applyVoucher(selectedVoucher)
        } ?: clearVoucherSelection()

        FileUtils.saveOrderToFile(cartOrder, requireContext())
    }

    private fun applyVoucher(voucher: Voucher) {
        when (voucher.type) {
            Voucher.TYPE_DISCOUNT -> cartOrder.discountVoucher = voucher
            Voucher.TYPE_FREE_COFFEE -> {
                cartOrder.coffeeVoucher = voucher
                addFreeCoffeeIfNecessary()
            }
        }
    }

    private fun addFreeCoffeeIfNecessary() {
        val hasFreeCoffee = cartOrder.cartProducts.any { it.name == "Free Coffee" }
        if (!hasFreeCoffee) {
            cartOrder.cartProducts.add(CartProduct("Free Coffee", 0.0, R.drawable.cappucino, "Coffee", 1))
        }
    }

    private fun clearVoucherSelection() {
        if (voucherType == Voucher.TYPE_DISCOUNT) cartOrder.discountVoucher = null
        if (voucherType == Voucher.TYPE_FREE_COFFEE) {
            cartOrder.coffeeVoucher = null
            cartOrder.cartProducts.removeAll { it.name == "Free Coffee" }
        }
    }

    private fun navigateToCartFragment() {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fLayout, CartFragment())
            commit()
        }
    }

    private fun setupActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Vouchers"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    companion object {
        private const val ARG_VOUCHER_TYPE = "voucher_type"

        fun newInstance(voucherType: String): VouchersApplyFragment {
            val fragment = VouchersApplyFragment()
            val args = Bundle().apply {
                putString(ARG_VOUCHER_TYPE, voucherType)
            }
            fragment.arguments = args
            return fragment
        }
    }
}