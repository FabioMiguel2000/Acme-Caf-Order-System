package com.feup.coffee_order_terminal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_terminal.R
import com.feup.coffee_order_terminal.core.service.ServiceLocator
import com.feup.coffee_order_terminal.domain.model.Order
import com.feup.coffee_order_terminal.ui.adapter.OrderListAdapter
import java.time.Instant
import java.time.format.DateTimeParseException

class OrderListFragment : Fragment() {
    private lateinit var orderListAdapter: OrderListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderListAdapter = OrderListAdapter(mutableListOf())

        setupRecyclerView(view)

        setupActionBar()

        ServiceLocator.orderRepository.getOrders { orders ->
            orders?.let {
                val verifiedOrder = orders.filter { it.status == Order.STATUS_VERIFIED }
                // Sorting the orders by date, most recent first
                val sortedOrders = verifiedOrder.sortedByDescending {
                    try {
                        Instant.parse(it.date)
                    } catch (e: DateTimeParseException) {
                        Instant.MIN
                    }
                }
                orderListAdapter.orders.clear()
                orderListAdapter.orders.addAll(sortedOrders)
                orderListAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupRecyclerView(view: View) {
        view.findViewById<RecyclerView>(R.id.rv_orders).apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = orderListAdapter
        }
    }

    private fun setupActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Orders"
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
    }


}