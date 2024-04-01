package com.feup.coffee_order_application.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.models.VoucherData

class AllVouchersAdapter(private val vouchers: MutableList<VoucherData>) :
    RecyclerView.Adapter<AllVouchersAdapter.AllVoucherViewHolder>() {

    class AllVoucherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val voucherName: TextView = view.findViewById(R.id.tv_voucher_name)
        val voucherCode: TextView = view.findViewById(R.id.tv_voucher_code)
        val radioBtnSelected: ImageView = view.findViewById(R.id.img_radio_btn_selected)
        val radioBtn: ImageView = view.findViewById(R.id.img_radio_btn)
        val cardLayout: ConstraintLayout = view.findViewById(R.id.voucher_card_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllVoucherViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.voucher_card, parent, false)
        return AllVoucherViewHolder(view)
    }

    override fun getItemCount() = vouchers.size
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AllVoucherViewHolder, position: Int) {
        val voucher = vouchers[position]

        holder.voucherName.text = if (voucher.type == "Discount") "5 % OFF" else "1 Free Coffee"
        holder.voucherCode.text = voucher._id
        holder.radioBtnSelected.visibility = View.GONE
        holder.radioBtn.visibility = View.GONE

        if (voucher.used) {
            holder.cardLayout.setBackgroundColor(Color.parseColor("#DBD8DD"))
        }

    }


}
