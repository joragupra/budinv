package com.joragupra.budinv.android.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joragupra.budinv.android.api.LedgerApi
import com.joragupra.budinv.android.api.LedgerDto
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
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

    private var loadJob: Job? = null

    init {
        loadLedger()
    }

    fun loadLedger() {
        loadJob?.cancel()
        _uiState.value = LedgerUiState.Loading
        loadJob = viewModelScope.launch {
            _uiState.value = try {
                LedgerUiState.Success(api.getLedger())
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                Log.e("LedgerViewModel", "Failed to load ledger", e)
                LedgerUiState.Error("Unable to load budget data. Please try again.")
            }
        }
    }
}
