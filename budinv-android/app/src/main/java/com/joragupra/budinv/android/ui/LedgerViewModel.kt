package com.joragupra.budinv.android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joragupra.budinv.android.domain.LedgerRepository
import com.joragupra.budinv.domain.ExpenseConcept
import com.joragupra.budinv.domain.Income
import com.joragupra.budinv.domain.IncurredExpense
import com.joragupra.budinv.domain.Ledger
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

sealed class LedgerUiState {
    data object Loading : LedgerUiState()
    data class Success(val ledger: Ledger) : LedgerUiState()
    data class Error(val message: String) : LedgerUiState()
}

class LedgerViewModel(private val repository: LedgerRepository) : ViewModel() {

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
                LedgerUiState.Success(repository.getLedger())
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                LedgerUiState.Error("Unable to load budget data. Please try again.")
            }
        }
    }

    fun addIncome(amount: Double, date: LocalDate, comments: String?) {
        viewModelScope.launch {
            val entry = Income().apply {
                setAmount(amount)
                setIncurredDate(date)
                setLogDate(date)
                setComments(comments)
            }
            repository.addEntry(entry)
            _uiState.value = LedgerUiState.Success(repository.getLedger())
        }
    }

    fun addExpense(amount: Double, date: LocalDate, comments: String?) {
        viewModelScope.launch {
            val entry = IncurredExpense(ExpenseConcept("Miscellaneous")).apply {
                setAmount(amount)
                setIncurredDate(date)
                setLogDate(date)
                setComments(comments)
            }
            repository.addEntry(entry)
            _uiState.value = LedgerUiState.Success(repository.getLedger())
        }
    }
}
