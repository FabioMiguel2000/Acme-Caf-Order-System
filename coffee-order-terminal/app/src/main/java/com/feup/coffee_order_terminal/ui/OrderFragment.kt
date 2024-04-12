package com.feup.coffee_order_terminal.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_terminal.R
import com.feup.coffee_order_terminal.models.ProductOrder
import com.feup.coffee_order_terminal.service.OrderApiCommunicator
import com.feup.coffee_order_terminal.ui.adapter.ProductAdapter

class OrderFragment : Fragment() {
    private val products = mutableListOf<ProductOrder>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
        getOrder()
    }

    private fun getOrder(){
        val orderApiCommunicator = OrderApiCommunicator()
        orderApiCommunicator.getOrder( requireContext(), "752d04417455bbffa4af0b7b9fa58750" ){
            order -> order.let {
                Log.e("PRODUCT", order.toString())
                products.clear()
                products.addAll(it!!.products)
                updateRecyclerView()
            }

            changeOrderInformation(order!!.client.nif, order.client.email, order.client.name)
        }
    }

    private fun setupRecyclerView(view: View){
        val adapter = ProductAdapter(mutableListOf())
        view.findViewById<RecyclerView>(R.id.rv_product_list).apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }
    private fun updateRecyclerView() {
        if(isAdded){
            val recyclerView = requireView().findViewById<RecyclerView>(R.id.rv_product_list)
            Log.e("productUpdate", this.products.toString())
            (recyclerView.adapter as? ProductAdapter)?.let { adapter ->
                adapter.products.clear()
                Log.e("productUpdate", this.products.toString())
                adapter.products.addAll(products)
                adapter.notifyDataSetChanged() // Notify the adapter of the data change - there's a pub sub inside
            }
        }
    }

    private fun changeOrderInformation(nif: String, email: String, name: String){
        val orderUser: TextView = requireView().findViewById(R.id.order_owner)
        val orderUserEmail: TextView = requireView().findViewById(R.id.order_owner_email)
        val orderUserName: TextView = requireView().findViewById(R.id.order_owner_name)
        orderUserEmail.text = email
        orderUser.text = nif
        orderUserName.text = name

        //falta order information()
        //Id
        //data
        //vouchers
        //summary
        //botão validar
    }
}