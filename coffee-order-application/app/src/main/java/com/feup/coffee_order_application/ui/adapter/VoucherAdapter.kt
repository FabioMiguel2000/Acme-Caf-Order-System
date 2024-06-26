package com.feup.coffee_order_application.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.feup.coffee_order_application.R
import com.feup.coffee_order_application.domain.model.Voucher

class VoucherAdapter(val vouchers: MutableList<Voucher>) :
    RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder>() {
    class VoucherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val voucherName: TextView = view.findViewById(R.id.tv_voucher_name)
        val voucherCode: TextView = view.findViewById(R.id.tv_voucher_code)
        val radioBtnSelected: ImageView = view.findViewById(R.id.img_radio_btn_selected)
        val radioBtn: ImageView = view.findViewById(R.id.img_radio_btn)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoucherViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.voucher_card, parent, false)
        return VoucherViewHolder(view)
    }
    override fun getItemCount() = vouchers.size
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VoucherViewHolder, position: Int) {
        val voucher = vouchers[position]
        Log.d("VoucherAdapter", "Voucher: $voucher")

        holder.voucherName.text = if (voucher.type == Voucher.TYPE_DISCOUNT) "5 % OFF" else "1 Free Coffee"
        holder.voucherCode.text = voucher._id
        holder.radioBtnSelected.visibility = if (voucher.isSelected) View.VISIBLE else View.GONE

        if (holder.radioBtnSelected.visibility == View.VISIBLE) {
            holder.radioBtnSelected.setOnClickListener {
                voucher.isSelected = false
                notifyDataSetChanged()
            }
        }
        holder.radioBtn.setOnClickListener {
            for (v in vouchers) {
                v.isSelected = false
            }
            voucher.isSelected = true
            notifyDataSetChanged()
        }
    }
}
