package com.feup.coffee_order_application.ui.fragment


import android.annotation.SuppressLint
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
import com.feup.coffee_order_application.ui.adapter.VoucherListAdapter
import com.feup.coffee_order_application.domain.model.Voucher
import com.feup.coffee_order_application.core.service.ServiceLocator
import com.feup.coffee_order_application.core.service.SessionManager
import com.feup.coffee_order_application.ui.adapter.ReceiptListAdapter


class ReceiptListFragment : Fragment() {
    private var userId: String = ""
//    private val vouchers = mutableListOf<Voucher>()
    private lateinit var receiptListAdapter: ReceiptListAdapter
//    private lateinit var unusedVoucherAdapter: VoucherListAdapter
//    private lateinit var usedVoucherAdapter: VoucherListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_receipt_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionManager = SessionManager(requireContext())
        userId = sessionManager.fetchUserToken() ?: ""
        receiptListAdapter = ReceiptListAdapter(mutableListOf())

        setupRecyclerView(view)

        setupActionBar()

        ServiceLocator.orderRepository.getOrdersByClientId(userId) { fetchedOrders ->
            fetchedOrders?.let {
//                Log.d("fetchedOrders", fetchedOrders.toString())
                receiptListAdapter.receipts.clear()
                receiptListAdapter.receipts.addAll(it)
                receiptListAdapter.notifyDataSetChanged()
            }
        }
    }


    private fun setupRecyclerView(view: View) {
        view.findViewById<RecyclerView>(R.id.rv_receipts).apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = receiptListAdapter
        }
    }

    private fun setupActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Receipts"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

}