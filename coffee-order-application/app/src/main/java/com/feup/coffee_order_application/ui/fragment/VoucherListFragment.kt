package com.feup.coffee_order_application.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.ui.adapter.VoucherListAdapter
import com.feup.coffee_order_application.domain.model.Voucher
import com.feup.coffee_order_application.core.service.ServiceLocator
import com.feup.coffee_order_application.core.service.SessionManager


class VoucherListFragment : Fragment() {
    private var userId: String = ""
    private val vouchers = mutableListOf<Voucher>()
    private lateinit var unusedVoucherAdapter: VoucherListAdapter
    private lateinit var usedVoucherAdapter: VoucherListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voucher_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionManager = SessionManager(requireContext())
        userId = sessionManager.fetchUserToken() ?: ""

        unusedVoucherAdapter = VoucherListAdapter(mutableListOf())
        usedVoucherAdapter = VoucherListAdapter(mutableListOf())

        setupRecyclerView(view)

        fetchVouchers()

        setupActionBar()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchVouchers(){
        ServiceLocator.userRepository.getUserVouchers(userId) { fetchedVouchers ->
            fetchedVouchers?.let {
                vouchers.clear()
                vouchers.addAll(it)

                val usedVouchers = vouchers.filter { voucher -> voucher.used }.toMutableList()
                val unusedVouchers = vouchers.filter { voucher -> !voucher.used }.toMutableList()

                unusedVoucherAdapter.vouchers.clear()
                unusedVoucherAdapter.vouchers.addAll(unusedVouchers)
                unusedVoucherAdapter.notifyDataSetChanged()

                usedVoucherAdapter.vouchers.clear()
                usedVoucherAdapter.vouchers.addAll(usedVouchers)
                usedVoucherAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupRecyclerView(view: View) {
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