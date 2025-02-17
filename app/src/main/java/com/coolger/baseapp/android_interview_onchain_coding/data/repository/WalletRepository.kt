package com.coolger.baseapp.android_interview_onchain_coding.data.repository

import com.google.gson.Gson
import kotlinx.coroutines.flow.*
import android.content.Context
import com.coolger.baseapp.android_interview_onchain_coding.data.model.Currency
import com.coolger.baseapp.android_interview_onchain_coding.data.model.ExchangeRate
import com.coolger.baseapp.android_interview_onchain_coding.data.model.WalletBalance

class WalletRepository(private val context: Context) {
    private val gson = Gson()

    // 支持的货币（过滤BTC/ETH/CRO）
    private val supportedCurrencies = listOf("BTC", "ETH", "CRO")

    fun getCurrencies(): Flow<List<Currency>> = flow {
        val json = context.assets.open("supported_currencies.json").reader().readText()
        val data = gson.fromJson(json, CurrenciesResponse::class.java)
        val filtered = data.currencies
            .filter { supportedCurrencies.contains(it.code) }
            .map { Currency(it.code, it.name, it.symbol) }
        emit(filtered)
    }

    fun getExchangeRates(): Flow<List<ExchangeRate>> = flow {
        val json = context.assets.open("exchange_rates.json").reader().readText()
        val data = gson.fromJson(json, RatesResponse::class.java)
        val rates = data.tiers
            .filter { it.to_currency == "USD" && supportedCurrencies.contains(it.from_currency) }
            .map {
                ExchangeRate(
                    it.from_currency,
                    it.to_currency,
                    it.rates.first().rate.toDouble()
                )
            }
        emit(rates)
    }

    fun getBalances(): Flow<List<WalletBalance>> = flow {
        val json = context.assets.open("wallet_balances.json").reader().readText()
        val data = gson.fromJson(json, BalanceResponse::class.java)
        val filtered = data.wallet
            .filter { supportedCurrencies.contains(it.currency) }
            .map { WalletBalance(it.currency, it.amount) }
        emit(filtered)
    }

    // Response classes for Gson
    private data class CurrenciesResponse(
        val currencies: List<CurrencyResponse>,
        val total: Int,
        val ok: Boolean
    )

    private data class CurrencyResponse(
        val code: String,
        val name: String,
        val symbol: String,
        // 其他字段省略
    )

    private data class RatesResponse(
        val ok: Boolean,
        val tiers: List<RateTier>
    )

    private data class RateTier(
        val from_currency: String,
        val to_currency: String,
        val rates: List<Rate>
    )

    private data class Rate(
        val amount: String,
        val rate: String
    )

    private data class BalanceResponse(
        val ok: Boolean,
        val wallet: List<Balance>
    )

    private data class Balance(
        val currency: String,
        val amount: Double
    )
}