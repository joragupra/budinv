package com.joragupra.budinv.android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joragupra.budinv.android.api.LedgerApi
import com.joragupra.budinv.android.api.LedgerDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class LedgerUiState {
    data object Loading : LedgerUiState()
    data class Success(val ledger: LedgerDto) : LedgerUiState()
    data class Error(val message: String) : LedgerUiState()
}

class LedgerViewModel(private val api: LedgerApi) : ViewModel() {

    private val _uiState = MutableStateFlow<LedgerUiState>(LedgerUiState.Loading)
    val uiState: StateFlow<LedgerUiState> = _uiState

    init {
        loadLedger()
    }

    fun loadLedger() {
        _uiState.value = LedgerUiState.Loading
        viewModelScope.launch {
            _uiState.value = try {
                LedgerUiState.Success(api.getLedger())
            } catch (e: Exception) {
                LedgerUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
