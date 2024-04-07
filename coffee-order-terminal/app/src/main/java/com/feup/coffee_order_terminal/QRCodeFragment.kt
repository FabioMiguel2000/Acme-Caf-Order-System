package com.feup.coffee_order_terminal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.feup.coffee_order_terminal.databinding.QrcodeReaderFragmentBinding
import com.feup.coffee_order_terminal.models.CoffeeVoucher
import com.feup.coffee_order_terminal.models.DiscountVoucher
import com.feup.coffee_order_terminal.models.Order
import com.feup.coffee_order_terminal.models.Product
import com.feup.coffee_order_terminal.models.ProductItem
import com.feup.coffee_order_terminal.service.OrderApiComunicator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import org.json.JSONObject
import java.util.Objects

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
                    val client = obj.getString("client")//ok
                    val status = obj.getString("status")//ok
                    val products = obj.getString("cartProducts")
                    val pdt: List<ProductItem> = gson.fromJson(products, object : TypeToken<List<ProductItem>>() {}.type)
                    val coffeeVoucher = obj.getString("coffeeVoucher")
                    val discountVoucher = obj.getString("discountVoucher")
                    val cfv = gson.fromJson(coffeeVoucher, CoffeeVoucher::class.java)//ok
                    val dtv = gson.fromJson(coffeeVoucher, DiscountVoucher::class.java)//ok

                    val orderApiComunicator = OrderApiComunicator()
                    //continuar aqui
                    orderApiComunicator.validateOrder(requireContext(), client, status, pdt, cfv, dtv)

                    Log.e("result", cfv.toString())
                    Toast.makeText(requireActivity(), "Order validated", Toast.LENGTH_LONG).show();
                    //make here http request to create order on the server
                } catch (e: JSONException) {
                    Log.e("error", e.toString())
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}