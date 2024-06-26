package com.feup.coffee_order_terminal.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_terminal.R
import com.feup.coffee_order_terminal.core.utils.DateFormatter
import com.feup.coffee_order_terminal.domain.model.CartProduct
import com.feup.coffee_order_terminal.service.OrderApiCommunicator
import com.feup.coffee_order_terminal.ui.adapter.ProductsAdapter

class OrderConfirmationFragment : Fragment() {
    var orderId = ""
    private val products = mutableListOf<CartProduct>()

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

        setupActionBar()
        this.orderId = this.arguments?.getString("order").toString()
        getOrder()

        val btnValidate: Button = requireView().findViewById(R.id.btn_validate_order)
        btnValidate.setOnClickListener {
            val orderApiCommunicator = OrderApiCommunicator()
            this.orderId?.let { it1 ->
                orderApiCommunicator.validateOrder(
                    this.requireContext(),
                    it1
                )
                // Navigate to the SuccessFragment
                val fragment = SuccessFragment()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fLayout, fragment)
                transaction.addToBackStack(null)
                transaction.commit()

            }
        }
    }

    private fun getOrder() {
        val orderApiCommunicator = OrderApiCommunicator()
        this.orderId.let {
            orderApiCommunicator.getOrder(requireContext(), it) {

                order ->
                order.let {
                    products.clear()
                    products.addAll(it!!.products)
                    updateRecyclerView()
                }
                changeOrderInformation(
                    order!!.client!!.nif,
                    order.client!!.email,
                    order.client!!.name,
                    order._id!!,
                    order.date!!,
                    order.total.toString(),
                    order.subtotal.toString(),
                    order.promotionDiscount.toString(),
                    order?.freeCoffeeVoucher?._id,
                    order?.discountVoucher?._id
                )
            }
        }
    }

    private fun setupRecyclerView(view: View) {
        val adapter = ProductsAdapter(mutableListOf())
        view.findViewById<RecyclerView>(R.id.rv_product_list).apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    private fun updateRecyclerView() {
        if (isAdded) {
            val recyclerView = requireView().findViewById<RecyclerView>(R.id.rv_product_list)
            (recyclerView.adapter as? ProductsAdapter)?.let { adapter ->
                adapter.products.clear()
                adapter.products.addAll(products)
                adapter.notifyDataSetChanged() // Notify the adapter of the data change - there's a pub sub inside
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun changeOrderInformation(
        nif: String,
        email: String,
        name: String,
        orderId: String,
        date: String,
        total: String,
        subTotal: String,
        orderPromotionDiscount: String,
        freeCoffeeVoucher: String?,
        discountVoucher: String?
    ) {
        val orderUser: TextView = requireView().findViewById(R.id.order_owner)
        val orderUserEmail: TextView = requireView().findViewById(R.id.order_owner_email)
        val orderUserName: TextView = requireView().findViewById(R.id.order_owner_name)
        val orderIdentifier: TextView = requireView().findViewById(R.id.order_number_id)
        val orderDate: TextView = requireView().findViewById(R.id.order_date)
        val orderTotal: TextView = requireView().findViewById(R.id.tv_total)
        val orderSubTotal: TextView = requireView().findViewById(R.id.order_subtotal_price)
        val promotionDiscount: TextView = requireView().findViewById(R.id.order_promotion_discount)
        val freeCoffe: TextView = requireView().findViewById(R.id.order_coffe_voucher_code)
        val discountVoucherV: TextView =
            requireView().findViewById(R.id.order_discount_voucher_code)
        orderUserEmail.text = "Email: $email"
        orderUser.text = "VAT Number: $nif"
        orderUserName.text = "Name: $name"
        orderIdentifier.text = "#${orderId.uppercase()}"
        orderDate.text = DateFormatter().formatDate(date)
        orderTotal.text = total
        orderSubTotal.text = subTotal
        promotionDiscount.text = orderPromotionDiscount
        freeCoffe.text = freeCoffeeVoucher?.uppercase() ?: "No Voucher"
        discountVoucherV.text = discountVoucher?.uppercase() ?: "No Voucher"
        this.orderId = orderId
    }

    private fun setupActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Order Validation"
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
    }
}