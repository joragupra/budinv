package com.joragupra.budinv.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.joragupra.budinv.android.api.RetrofitClient
import com.joragupra.budinv.android.theme.BudInvTheme
import com.joragupra.budinv.android.ui.LedgerScreen
import com.joragupra.budinv.android.ui.LedgerViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: LedgerViewModel by lazy {
        ViewModelProvider(this, LedgerViewModelFactory())[LedgerViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BudInvTheme {
                LedgerScreen(viewModel = viewModel)
            }
        }
    }
}

private class LedgerViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        LedgerViewModel(RetrofitClient.ledgerApi) as T
}
