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
import com.feup.coffee_order_application.adapters.AllVouchersAdapter
import com.feup.coffee_order_application.adapters.VoucherAdapter
import com.feup.coffee_order_application.models.Voucher


class AllVouchersFragment : Fragment() {
    val vouchers = mutableListOf<Voucher>(
        Voucher("9768b993ae44ecea8dfde6439349f1c2", "discount", "dasdadasda", false, false),
        Voucher("3813e7553135d09e6b993f39251e73ab", "discount", "dasdadasda", false, false),
        Voucher("e3f63421c2da473da3a7838408613889", "coffee", "dasdadasda", true, false),
        Voucher("5f2af742faf4aec14441efa7fb31aa47", "coffee", "dasdadasda", false, false),)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_vouchers, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()
        setupRecyclerView(view)
    }

    private fun setupRecyclerView(view: View) {
        val usedVouchers = vouchers.filter { it.used }.toMutableList()
        val unusedVoucher = vouchers.filter { !it.used }.toMutableList()
        val usedVoucherAdapter = AllVouchersAdapter(usedVouchers)
        val unusedVoucherAdapter = AllVouchersAdapter(unusedVoucher)



        view.findViewById<RecyclerView>(R.id.rv_used_vouchers).apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = usedVoucherAdapter
        }

        view.findViewById<RecyclerView>(R.id.rv_unused_vouchers).apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = unusedVoucherAdapter
        }
    }

    private fun setupActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "All Vouchers"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

}