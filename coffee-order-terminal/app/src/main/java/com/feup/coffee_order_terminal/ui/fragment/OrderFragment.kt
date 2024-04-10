package com.feup.coffee_order_terminal.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.feup.coffee_order_terminal.R
import com.feup.coffee_order_terminal.core.ServiceLocator
import com.feup.coffee_order_terminal.domain.model.Order

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
    }

    private fun updateRecyclerView() {
    }

}