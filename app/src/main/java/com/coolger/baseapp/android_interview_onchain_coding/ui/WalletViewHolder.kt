package com.coolger.baseapp.android_interview_onchain_coding.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coolger.baseapp.android_interview_onchain_coding.R
import com.coolger.baseapp.android_interview_onchain_coding.data.model.WalletBalance

class WalletViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: WalletBalance) {
        itemView.findViewById<TextView>(R.id.currencyName).text = item.currency
        itemView.findViewById<TextView>(R.id.amount).text = "${item.amount} ${item.currency}"
        itemView.findViewById<TextView>(R.id.usdValue).text = "$${String.format("%.2f", item.usdValue)}"
    }
}