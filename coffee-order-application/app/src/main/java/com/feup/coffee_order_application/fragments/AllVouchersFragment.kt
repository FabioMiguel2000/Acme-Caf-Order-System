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
import com.feup.coffee_order_application.adapters.AllVouchersAdapter
import com.feup.coffee_order_application.adapters.VoucherAdapter
import com.feup.coffee_order_application.models.Voucher
import com.feup.coffee_order_application.models.VoucherData
import com.feup.coffee_order_application.services.ServiceLocator


class AllVouchersFragment : Fragment() {
    private var userId: String = "31ca6621550a71fdb4629390d1d264a2" // hardcoded user id, TODO: get from shared preferences (session)
    private val vouchers = mutableListOf<VoucherData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_vouchers, container, false)
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
                setupRecyclerView(requireView())
            }
        }
    }

    private fun setupRecyclerView(view: View) {
        val usedVouchers = this.vouchers.filter { it.used }.toMutableList()
        val unusedVoucher = this.vouchers.filter { !it.used }.toMutableList()

        val unusedVoucherAdapter = AllVouchersAdapter(unusedVoucher)
        val usedVoucherAdapter = AllVouchersAdapter(usedVouchers)

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