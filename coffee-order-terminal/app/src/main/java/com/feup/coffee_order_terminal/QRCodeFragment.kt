package com.feup.coffee_order_terminal

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.feup.coffee_order_terminal.databinding.QrcodeReaderFragmentBinding
import com.feup.coffee_order_terminal.models.CoffeeVoucher
import com.feup.coffee_order_terminal.models.DiscountVoucher
import com.feup.coffee_order_terminal.models.ProductCartItem
import com.feup.coffee_order_terminal.service.OrderApiCommunicator
import com.feup.coffee_order_terminal.ui.OrderFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import org.json.JSONObject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class QRCodeFragment : Fragment() {

    var qrScanIntegrator : IntentIntegrator? = null
    private var _binding: QrcodeReaderFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = QrcodeReaderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpScanner()
        setOnClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpScanner(){
        qrScanIntegrator = IntentIntegrator.forSupportFragment(this)
        qrScanIntegrator?.setOrientationLocked(false)
    }

    private fun setOnClickListener(){
        binding.btnOpen.setOnClickListener{performAction()}
    }

    private fun performAction(){
        qrScanIntegrator?.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(result != null){
            //no data
            if(result.contents == null){
                Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_LONG).show()
            } else {
                try {
                    val gson = Gson();
                    val obj = JSONObject(result.contents)
                    var client = "";
                    var status = "Pending"
                    var discountVoucher  = ""
                    var freeCoffeeVoucher = ""
                    var products: List<ProductCartItem> = emptyList()
                    var discountVoucherObj: DiscountVoucher? = null
                    var freeCoffeeVoucherObj: CoffeeVoucher? = null
                    if(obj.has("client")){
                        client = obj.getString("client")//ok
                    }

                    if(obj.has("status")){
                        status = obj.getString("status")
                    }

                    if(obj.has("products")){
                        val cartProducts = obj.getString("products")
                        products = gson.fromJson(cartProducts, object : TypeToken<List<ProductCartItem>>() {}.type)
                    }

                    if(obj.has("coffeeVoucher")){
                        freeCoffeeVoucher = obj.getString("coffeeVoucher")
                        freeCoffeeVoucherObj = gson.fromJson(freeCoffeeVoucher, CoffeeVoucher::class.java)
                        val coffeeVoucherId  =freeCoffeeVoucher
                    }

                    if(obj.has("discountVoucher")){
                        discountVoucher = obj.getString("coffeeVoucher")
                        discountVoucherObj = gson.fromJson(discountVoucher, DiscountVoucher::class.java)
                        val discountVoucherId = discountVoucher
                    }

                    val builder = AlertDialog.Builder(this.requireContext())
                    builder.setMessage("Acept order?")
                    builder.setCancelable(false)
                    builder.setPositiveButton("Yes")   {
                            _, _ ->
                        run {
                            val orderApiCommunicator = OrderApiCommunicator()
                            val args = Bundle()

                            orderApiCommunicator.createOrder(requireContext(), client, status, products, freeCoffeeVoucher, discountVoucher) {
                                order -> order
                                if(order !== null) {
                                    val orderInfo = OrderFragment()
                                    args.putString("order", order._id)
                                    orderInfo.arguments = args
                                    fragmentManager?.beginTransaction()?.replace(R.id.qr_code_reader_fragment, orderInfo)?.commit()
                                    Log.e("callbackOrder", order.toString())
                                } else {
                                    Log.e("callbackOrder", "Sem resultados a mostrar")
                                }
                            }
                        }
                    }

                     builder.setNegativeButton("Not") {
                             dialog, _ -> dialog.cancel()
                     }
                    val alertDialog = builder.create()
                    alertDialog.show()
                } catch (e: JSONException) {
                    Log.e("error", e.toString())
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}