package com.coolger.baseapp.android_interview_onchain_coding.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.coolger.baseapp.android_interview_onchain_coding.R
import com.coolger.baseapp.android_interview_onchain_coding.data.repository.WalletRepository
import com.coolger.baseapp.android_interview_onchain_coding.data.viewmodel.WalletViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: WalletViewModel
    private lateinit var adapter: WalletAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repo = WalletRepository(applicationContext)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return WalletViewModel(repo) as T
            }
        })[WalletViewModel::class.java]

        adapter = WalletAdapter()
        findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter

        viewModel.walletItems
            .onEach { adapter.submitList(it) }
            .launchIn(lifecycleScope)
    }
}