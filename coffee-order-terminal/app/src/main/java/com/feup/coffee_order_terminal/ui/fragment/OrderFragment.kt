package com.feup.coffee_order_terminal.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_terminal.R
import com.feup.coffee_order_terminal.domain.model.Order
import com.feup.coffee_order_terminal.ui.adapter.OrdersAdapter

class OrderFragment : Fragment() {
    val orders = mutableListOf<Order>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val order = arguments?.getParcelable<Order>("order")
        order?.let {
            orders.add(it)
        }
        setupActionBar()
        setupRecyclerView(view)
        updateRecyclerView()
    }
    private fun setupRecyclerView(view: View) {
        val adapter = OrdersAdapter(mutableListOf())
        view.findViewById<RecyclerView>(R.id.rv_orders).apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            this.adapter = adapter
        }
    }

    private fun setupActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Order"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun updateRecyclerView() {

        val recyclerView = requireView().findViewById<RecyclerView>(R.id.rv_orders)
        (recyclerView.adapter as? OrdersAdapter)?.let { adapter ->
            adapter.orders.clear()
            adapter.orders.addAll(this.orders)
            adapter.notifyDataSetChanged()
        }

    }
}