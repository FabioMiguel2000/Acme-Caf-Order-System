package com.feup.coffee_order_terminal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_terminal.databinding.OrderListFragmentBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class OrderList : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.order_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)
    }
    private fun setupRecyclerView(view: View) {
       //set list of orders items
    }

    private fun updateRecyclerView() {
        //update list of orders items
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}