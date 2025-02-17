package com.coolger.baseapp.android_interview_onchain_coding.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.coolger.baseapp.android_interview_onchain_coding.R
import com.coolger.baseapp.android_interview_onchain_coding.data.model.WalletBalance

class WalletAdapter : ListAdapter<WalletBalance, WalletViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WalletViewHolder(inflater.inflate(R.layout.item_wallet, parent, false))
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<WalletBalance>() {
        override fun areItemsTheSame(oldItem: WalletBalance, newItem: WalletBalance) =
            oldItem.currency == newItem.currency

        override fun areContentsTheSame(oldItem: WalletBalance, newItem: WalletBalance) =
            oldItem == newItem
    }
}