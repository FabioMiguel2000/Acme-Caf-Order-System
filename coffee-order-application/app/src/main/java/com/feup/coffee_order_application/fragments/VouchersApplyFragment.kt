package com.feup.coffee_order_application.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.adapters.VoucherAdapter
import com.feup.coffee_order_application.models.Order
import com.feup.coffee_order_application.models.Voucher
import com.feup.coffee_order_application.utils.FileUtils
import com.google.android.material.button.MaterialButton

val vouchers = mutableListOf<Voucher>(
    Voucher("9768b993ae44ecea8dfde6439349f1c2", "discount", "dasdadasda", false, false),
    Voucher("3813e7553135d09e6b993f39251e73ab", "discount", "dasdadasda", false, false),
    Voucher("e3f63421c2da473da3a7838408613889", "coffee", "dasdadasda", false, false),
    Voucher("5f2af742faf4aec14441efa7fb31aa47", "coffee", "dasdadasda", false, false),
)
class VouchersApplyFragment : Fragment() {
    private lateinit var cartOrder: Order
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

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Vouchers"

        val actionBar = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        val filteredVouchers: MutableList<Voucher> = vouchers.filter { it.type == voucherType }.toMutableList()

        val adapter = VoucherAdapter(filteredVouchers)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_vouchers)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        cartOrder = FileUtils.readOrderFromFile(requireContext())
        if (cartOrder.discountVoucher != null) {
            for (v in filteredVouchers) {
                if (v.uuid == cartOrder.discountVoucher!!.uuid) {
                    v.isSelected = true
                }
            }
        }
        Log.d("Coffee Voucher", cartOrder.coffeeVoucher.toString())
        if (cartOrder.coffeeVoucher != null) {
            for (v in filteredVouchers) {
                if (v.uuid == cartOrder.coffeeVoucher!!.uuid) {
                    v.isSelected = true
                }
            }
        }

        var applyVoucherBtn: MaterialButton = view.findViewById(R.id.btn_apply_voucher)
        applyVoucherBtn.setOnClickListener {
            for (v in vouchers) {
                if (v.isSelected) {
                    if (v.type == "discount") {
                        cartOrder.discountVoucher = v
                    }
                    else {
                        cartOrder.coffeeVoucher = v
                    }
                    FileUtils.saveOrderToFile(cartOrder, requireContext())

                    val fragmentManager = parentFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    val cartFragment = CartFragment()

                    fragmentTransaction.replace(R.id.fLayout, cartFragment)
                    fragmentTransaction.commit()

                    return@setOnClickListener
                }
            }
            // No Voucher Selected
            cartOrder.coffeeVoucher = null
            cartOrder.discountVoucher = null

            FileUtils.saveOrderToFile(cartOrder, requireContext())

            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val cartFragment = CartFragment()

            fragmentTransaction.replace(R.id.fLayout, cartFragment)
            fragmentTransaction.commit()
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