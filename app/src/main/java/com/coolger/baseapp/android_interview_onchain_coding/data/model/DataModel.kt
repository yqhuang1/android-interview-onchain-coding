package com.coolger.baseapp.android_interview_onchain_coding.data.model

data class Currency(
    val code: String,
    val name: String,
    val symbol: String
)

data class ExchangeRate(
    val fromCurrency: String,
    val toCurrency: String,
    val rate: Double
)

data class WalletBalance(
    val currency: String,
    val amount: Double,
    var usdValue: Double = 0.0
)