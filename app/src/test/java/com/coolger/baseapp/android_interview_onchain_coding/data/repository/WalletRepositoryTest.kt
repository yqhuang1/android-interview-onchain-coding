package com.coolger.baseapp.android_interview_onchain_coding.data.repository

import android.content.Context
import android.content.res.AssetManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.InputStream
import java.io.InputStreamReader

class WalletRepositoryTest {

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockAssetManager: AssetManager

    private lateinit var walletRepository: WalletRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(mockContext.assets).thenReturn(mockAssetManager)
        walletRepository = WalletRepository(mockContext)
    }

    @Test
    fun testGetCurrencies() = runBlocking {
        val mockJson = """
            {
                "currencies": [
                    {
                        "code": "BTC",
                        "name": "Bitcoin",
                        "symbol": "BTC"
                    },
                    {
                        "code": "ETH",
                        "name": "Ethereum",
                        "symbol": "ETH"
                    },
                    {
                        "code": "CRO",
                        "name": "Crypto.com Coin",
                        "symbol": "CRO"
                    }
                ],
                "total": 3,
                "ok": true
            }
        """.trimIndent()

        // 为每个测试创建新的InputStream模拟实例
        val mockInputStream = Mockito.mock(InputStream::class.java)
        Mockito.`when`(mockAssetManager.open("supported_currencies.json")).thenReturn(mockInputStream)
        Mockito.`when`(mockInputStream.reader()).thenReturn(InputStreamReader(mockJson.byteInputStream()))

        val currencies = walletRepository.getCurrencies().first()
        assertEquals(3, currencies.size)
    }

    @Test
    fun testGetExchangeRates() = runBlocking {
        val mockJson = """
            {
                "ok": true,
                "tiers": [
                    {
                        "from_currency": "BTC",
                        "to_currency": "USD",
                        "rates": [
                            {
                                "amount": "1",
                                "rate": "40000"
                            }
                        ]
                    },
                    {
                        "from_currency": "ETH",
                        "to_currency": "USD",
                        "rates": [
                            {
                                "amount": "1",
                                "rate": "2000"
                            }
                        ]
                    },
                    {
                        "from_currency": "CRO",
                        "to_currency": "USD",
                        "rates": [
                            {
                                "amount": "1",
                                "rate": "0.5"
                            }
                        ]
                    }
                ]
            }
        """.trimIndent()

        // 使用新的mockInputStream实例
        val mockInputStream = Mockito.mock(InputStream::class.java)
        Mockito.`when`(mockAssetManager.open("exchange_rates.json")).thenReturn(mockInputStream)
        Mockito.`when`(mockInputStream.reader()).thenReturn(InputStreamReader(mockJson.byteInputStream()))

        val exchangeRates = walletRepository.getExchangeRates().first()
        assertEquals(3, exchangeRates.size)
    }

    @Test
    fun testGetBalances() = runBlocking {
        val mockJson = """
            {
                "ok": true,
                "wallet": [
                    {
                        "currency": "BTC",
                        "amount": 1.0
                    },
                    {
                        "currency": "ETH",
                        "amount": 2.0
                    },
                    {
                        "currency": "CRO",
                        "amount": 3.0
                    }
                ]
            }
        """.trimIndent()

        // 使用新的mockInputStream实例
        val mockInputStream = Mockito.mock(InputStream::class.java)
        Mockito.`when`(mockAssetManager.open("wallet_balances.json")).thenReturn(mockInputStream)
        Mockito.`when`(mockInputStream.reader()).thenReturn(InputStreamReader(mockJson.byteInputStream()))

        val balances = walletRepository.getBalances().first()
        assertEquals(3, balances.size)
    }
}