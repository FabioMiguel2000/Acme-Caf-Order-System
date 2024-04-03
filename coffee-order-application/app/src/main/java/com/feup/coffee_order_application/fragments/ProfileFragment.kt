package com.feup.coffee_order_application.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.databinding.FragmentCartBinding
import com.feup.coffee_order_application.databinding.FragmentProfileBinding
import com.feup.coffee_order_application.models.ApiResponse
import com.feup.coffee_order_application.models.ResponseApi
import com.feup.coffee_order_application.models.User
import com.feup.coffee_order_application.services.ApiInterface
import com.feup.coffee_order_application.services.ServiceLocator
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileFragment : Fragment() {
    private var user_id: String = "ecf585f7874bc0d4c5f4f622dc93730b" // hardcoded user id, TODO: get from shared preferences (session)
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Hi User!"
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }

        fetchUserData()
        binding.vouchersOptionContainer.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fLayout, AllVouchersFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun fetchUserData() {
        ServiceLocator.userRepository.getUserById(user_id) { user ->
            user?.let {
                updateUI(it)
            }
        }
    }

    private fun updateUI(user: User) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Hi ${user.name}!"

        updateProgress(
            user.accumulatedCoffeeBuys.toInt(),
            MAX_COFFEE_BUYS,
            binding.tvDrinkProgressFraction,
            binding.freeDrinkProgressBar,
            binding.tvFreeDrinkProgressDesc,
            "More Drinks To Receive 1 Drink For Free"
        )

        // Update Discount Progress
        updateProgress(
            user.accumulatedExpenses.toInt(),
            MAX_EXPENSES_POINTS,
            binding.tvDiscountProgressFraction,
            binding.discountProgressBar,
            binding.tvDiscountProgressDesc,
            "More Points For Discount Voucher (1EUR = 1 Point)"
        )
    }

    @SuppressLint("SetTextI18n")
    private fun updateProgress(
        currentProgress: Int,
        maxProgress: Int,
        progressFractionTextView: TextView,
        progressBar: ProgressBar,
        progressDescTextView: TextView,
        progressDescSuffix: String
    ) {
        progressFractionTextView.text = "$currentProgress/$maxProgress"
        progressBar.progress = currentProgress
        val remainingProgress = maxProgress - currentProgress
        progressDescTextView.text = "$remainingProgress $progressDescSuffix"
    }
    companion object {
        private const val MAX_COFFEE_BUYS = 3
        private const val MAX_EXPENSES_POINTS = 100
    }
}