package com.feup.coffee_order_application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.adapters.CategoriesAdapter
import com.feup.coffee_order_application.adapters.VoucherAdapter
import com.feup.coffee_order_application.models.Category
import com.feup.coffee_order_application.models.Voucher

val vouchers = mutableListOf<Voucher>(
    Voucher("ajkadshkfbhkadcbage13h", "discount", "dasdadasda", false, false),
    Voucher("ajkadshfbhksadcbage13h", "discount", "dasdadasda", false, false),

)
class VouchersApply : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vouchers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Vouchers"

        val actionBar = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        val adapter = VoucherAdapter(vouchers)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_vouchers)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }


}