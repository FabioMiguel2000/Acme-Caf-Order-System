package com.feup.coffee_order_terminal.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_terminal.R
import com.feup.coffee_order_terminal.core.service.ServiceLocator
import com.feup.coffee_order_terminal.domain.model.Order
import com.feup.coffee_order_terminal.ui.adapter.OrdersAdapter

class OrderFragment : Fragment() {
    private val orders = mutableListOf<Order>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.order_list_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)

        ServiceLocator.orderRepository.getOrders { orders ->
            orders?.let {
                this.orders.clear()
                this.orders.addAll(it)
                updateRecyclerView()
            }
        }
    }
    private fun setupRecyclerView(view: View) {
        val adapter = OrdersAdapter(mutableListOf())
        view.findViewById<RecyclerView>(R.id.rv_orders).apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            this.adapter = adapter
        }
    }

    private fun updateRecyclerView() {
        if (isAdded) {
            val recyclerView = requireView().findViewById<RecyclerView>(R.id.rv_orders)
            (recyclerView.adapter as? OrdersAdapter)?.let { adapter ->
                adapter.orders.clear()
                adapter.orders.addAll(this.orders)
                adapter.notifyDataSetChanged()
            }
        }
    }
}