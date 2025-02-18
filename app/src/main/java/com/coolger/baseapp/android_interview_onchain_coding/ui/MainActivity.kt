package com.coolger.baseapp.android_interview_onchain_coding.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolger.baseapp.android_interview_onchain_coding.R
import com.coolger.baseapp.android_interview_onchain_coding.data.repository.WalletRepository
import com.coolger.baseapp.android_interview_onchain_coding.data.viewmodel.WalletViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: WalletViewModel
    private lateinit var walletAdapter: WalletAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repo = WalletRepository(applicationContext)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return WalletViewModel(repo) as T
            }
        })[WalletViewModel::class.java]

        walletAdapter = WalletAdapter(this)
        findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = walletAdapter

            // 添加分割线
            val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }

        lifecycleScope.launch {
            // 收集 StateFlow 中的数据
            viewModel.walletItems.collect { walletItemList ->
                // 计算 usdValue 总和
                val totalUsdValue = walletItemList.sumOf { it.usdValue }
                // 更新 UI 显示总和
                findViewById<TextView>(R.id.maintopBalance).text =
                    "$ " + "%.2f".format(totalUsdValue) + " USD"

                walletAdapter.submitList(walletItemList)
            }
        }

//        viewModel.walletItems
//            .onEach {
//                Log.d("MainActivity", "Received wallet items: ${it.size}")
//                adapter.submitList(it)
//            }
//            .launchIn(lifecycleScope)
    }
}