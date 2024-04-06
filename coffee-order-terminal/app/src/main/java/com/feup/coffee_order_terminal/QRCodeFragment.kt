package com.feup.coffee_order_terminal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.feup.coffee_order_terminal.databinding.QrcodeReaderFragmentBinding
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

        binding.btnOpen.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        setUpScanner()
        setOnClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpScanner(){
        qrScanIntegrator = IntentIntegrator.forSupportFragment(this)
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
                Log.e("scanning", "No data found");
            } else {
                try {
                    val obj = JSONObject(result.contents)
                    Log.e("result", obj.toString())
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}