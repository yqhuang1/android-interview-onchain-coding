package com.coolger.baseapp.android_interview_onchain_coding.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coolger.baseapp.android_interview_onchain_coding.R
import com.coolger.baseapp.android_interview_onchain_coding.data.constant.Constant
import com.coolger.baseapp.android_interview_onchain_coding.data.model.WalletBalance

class WalletAdapter(private val context: Context) :
    RecyclerView.Adapter<WalletAdapter.WalletViewHolder>() {

    private var walletItems: List<WalletBalance> = emptyList()

    fun submitList(newList: List<WalletBalance>) {
        Log.d("WalletAdapter", "Submitting list with size: ${newList.size}")
        walletItems = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wallet, parent, false)
        return WalletViewHolder(view)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        val item = walletItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = walletItems.size

    inner class WalletViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val currencyNameTextView: TextView = itemView.findViewById(R.id.currencyName)
        private val amountTextView: TextView = itemView.findViewById(R.id.amount)
        private val usdValueTextView: TextView = itemView.findViewById(R.id.usdValue)

        fun bind(item: WalletBalance) {
            val currency = item.currency
            currencyNameTextView.text = Constant.CURRENCY_NAME[currency]
                ?.let { context.getString(it) }
            amountTextView.text = item.amount.toString() + " " + currency
            usdValueTextView.text = "$ " + "%.2f".format(item.usdValue)
        }
    }
}