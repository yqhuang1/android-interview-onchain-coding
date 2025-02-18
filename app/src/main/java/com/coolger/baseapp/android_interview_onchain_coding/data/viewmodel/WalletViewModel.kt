package com.coolger.baseapp.android_interview_onchain_coding.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolger.baseapp.android_interview_onchain_coding.data.repository.WalletRepository
import kotlinx.coroutines.flow.*

class WalletViewModel(repo: WalletRepository) : ViewModel() {
    private val _currencies =
        repo.getCurrencies().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    private val _rates =
        repo.getExchangeRates().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    private val _balances =
        repo.getBalances().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val walletItems = combine(_balances, _rates) { balances, rates ->
        balances.map { balance ->
            val rate = rates.find { it.fromCurrency == balance.currency }?.rate ?: 0.0
            balance.copy(usdValue = balance.amount * rate)
        }.also {
            Log.d("WalletVM", "Combined data: $it")
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}