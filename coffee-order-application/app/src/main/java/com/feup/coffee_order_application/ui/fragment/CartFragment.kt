package com.feup.coffee_order_application.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.core.service.ServiceLocator
import com.feup.coffee_order_application.core.service.SessionManager
import com.feup.coffee_order_application.ui.adapter.CartAdapter
import com.feup.coffee_order_application.databinding.FragmentCartBinding
import com.feup.coffee_order_application.domain.model.Order
import com.feup.coffee_order_application.domain.model.Voucher
import com.feup.coffee_order_application.core.utils.OrderStorageUtils
import com.feup.coffee_order_application.domain.model.OrderRequest
import com.feup.coffee_order_application.domain.model.ProductItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.round

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private lateinit var cartOrder: Order
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        cartOrder = OrderStorageUtils.readOrderFromFile(requireContext())
        updateUI()
        setupRecyclerView()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartOrder = OrderStorageUtils.readOrderFromFile(requireContext())
        setupActionBar()
        setupRecyclerView()
        updateUI()
        setupListeners()
    }
    private fun setupRecyclerView() {
        val adapter = CartAdapter(cartOrder.products) {
            OrderStorageUtils.saveOrderToFile(cartOrder, requireContext())
            updatePrices()
            updateCartRendering(cartOrder.products.isEmpty())
        }

        binding.rvCart.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    private fun updateUI() {
        updatePrices()
        updateCartRendering(cartOrder.products.isEmpty())
        updateVoucherStatus()
    }
    private fun updateVoucherStatus() {
        binding.tvSelectCoffeeVoucher.text = getString(
            if (cartOrder.freeCoffeeVoucher == null) R.string.voucher_not_selected else R.string.voucher_selected
        )
        binding.tvSelectDiscountVoucher.text = getString(
            if (cartOrder.discountVoucher == null) R.string.voucher_not_selected else R.string.voucher_selected
        )
    }

    private fun setupListeners() {
        binding.btnGoShopNow.setOnClickListener {
            navigateToFragment(HomeFragment())

            val activity = requireActivity() as AppCompatActivity
            val bottomNavigationView: BottomNavigationView = activity.findViewById(R.id.bottom_nav)
            bottomNavigationView.selectedItemId = R.id.home
        }
        binding.discountVoucherContainer.setOnClickListener { navigateToFragment(VouchersApplyFragment.newInstance(
            Voucher.TYPE_DISCOUNT)) }
        binding.coffeeVoucherContainer.setOnClickListener { navigateToFragment(VouchersApplyFragment.newInstance(Voucher.TYPE_FREE_COFFEE)) }

        binding.btnCheckout.setOnClickListener {
//            createOrderAndNavigate()
            navigateToFragment(CheckoutFragment())
            Toast.makeText(requireContext(), "Order created successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createOrderAndNavigate (){
        val orderRequest = OrderRequest(
            client = SessionManager(requireContext()).fetchUserToken()!!,
            products = cartOrder.products.map { cartProduct -> ProductItem(cartProduct.product._id, cartProduct.quantity)  },
            discountVoucher = cartOrder.discountVoucher?._id ,
            freeCoffeeVoucher = cartOrder.freeCoffeeVoucher?._id
            )

        ServiceLocator.orderRepository.createOrder(orderRequest) { isSuccess, statusCode ->
            if (isSuccess) {
                navigateToFragment(CheckoutFragment())
                Toast.makeText(requireContext(), "Order created successfully", Toast.LENGTH_SHORT).show()
            } else {
                Log.e("CartFragment", "Failed to create order, status code: $statusCode")
                Toast.makeText(requireContext(), "Failed to create order, status code: $statusCode", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fLayout, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun setupActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Cart"
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
    }
    fun View.setVisible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }
    private fun updateCartRendering(isCartEmpty: Boolean) {
        binding.emptyCartContainer.setVisible(isCartEmpty)
        listOf(binding.coffeeVoucherContainer, binding.discountVoucherContainer, binding.bottomBoxContainer, binding.btnCheckout, binding.line1, binding.line2, binding.line3)
            .forEach { it.setVisible(!isCartEmpty) }
    }

    private fun updatePrices() {
        val subtotalPrice = calculateSubtotalPrice()
        val discountPrice = calculateDiscountPrice(subtotalPrice)
        val totalPrice = calculateTotalPrice(subtotalPrice, discountPrice)

        with(binding) {
            tvSubtotalPrice.text = getString(R.string.price_format, subtotalPrice)
            tvPromotionDiscount.text = getString(R.string.negative_price_format, discountPrice)
            tvTotal.text = getString(R.string.price_format, totalPrice)
        }
    }

    private fun calculateSubtotalPrice() = round(cartOrder.products.sumOf { it.product.price * it.quantity } * 100) / 100

    private fun calculateDiscountPrice(subtotalPrice: Double) =
        cartOrder.discountVoucher?.let { round(subtotalPrice * 0.05 * 100) / 100 } ?: 0.0

    private fun calculateTotalPrice(subtotalPrice: Double, discountPrice: Double) = subtotalPrice - discountPrice

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}