package com.feup.coffee_order_application.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.databinding.FragmentProfileBinding
import com.feup.coffee_order_application.domain.model.User
import com.feup.coffee_order_application.core.service.ServiceLocator
import com.feup.coffee_order_application.core.service.SessionManager
import com.feup.coffee_order_application.core.utils.OrderStorageUtils
import com.feup.coffee_order_application.ui.activity.LoginActivity

class ProfileFragment : Fragment() {
    private var userId: String = ""
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

        val sessionManager = SessionManager(requireContext())
        userId = sessionManager.fetchUserToken() ?: ""

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Hi User!"
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }

        fetchUserData()
        binding.vouchersOptionContainer.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fLayout, VoucherListFragment())
                addToBackStack(null)
                commit()
            }
        }

        binding.receiptsOptionContainer.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fLayout, ReceiptListFragment())
                addToBackStack(null)
                commit()
            }
        }

        binding.btnLogout.setOnClickListener {
            SessionManager(requireContext()).clearSession()
            OrderStorageUtils.clearOrderFile(requireContext())
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchUserData() {
        ServiceLocator.userRepository.getUserById(userId) { user ->
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