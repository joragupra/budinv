package com.joragupra.budinv.android

import app.cash.turbine.test
import com.joragupra.budinv.android.domain.InMemoryLedgerRepository
import com.joragupra.budinv.android.domain.LedgerRepository
import com.joragupra.budinv.android.ui.LedgerUiState
import com.joragupra.budinv.android.ui.LedgerViewModel
import com.joragupra.budinv.domain.BookkeepingEntry
import com.joragupra.budinv.domain.Income
import com.joragupra.budinv.domain.IncurredExpense
import com.joragupra.budinv.domain.Ledger
import java.time.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LedgerViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun shouldEmitLoadingThenSuccessWhenRepositorySucceeds() = runTest {
        val viewModel = LedgerViewModel(InMemoryLedgerRepository())

        viewModel.uiState.test {
            assertTrue(awaitItem() is LedgerUiState.Loading)
            assertTrue(awaitItem() is LedgerUiState.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldEmitLoadingThenErrorWhenRepositoryFails() = runTest {
        val viewModel = LedgerViewModel(FailingLedgerRepository())

        viewModel.uiState.test {
            assertTrue(awaitItem() is LedgerUiState.Loading)
            val error = awaitItem() as LedgerUiState.Error
            assertEquals("Unable to load budget data. Please try again.", error.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldReloadOnRetry() = runTest {
        val viewModel = LedgerViewModel(InMemoryLedgerRepository())
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            awaitItem() // current Success state

            viewModel.loadLedger()

            assertTrue(awaitItem() is LedgerUiState.Loading)
            assertTrue(awaitItem() is LedgerUiState.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldCancelPreviousJobOnRapidReload() = runTest {
        val viewModel = LedgerViewModel(InMemoryLedgerRepository())
        viewModel.loadLedger() // second call before first coroutine runs

        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is LedgerUiState.Success)
    }

    @Test
    fun shouldAddIncomeEntryAndUpdateState() = runTest {
        val viewModel = LedgerViewModel(InMemoryLedgerRepository())
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.addIncome(1500.0, LocalDate.now(), null)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value as LedgerUiState.Success
        assertEquals(1, state.ledger.entries.size)
        assertTrue(state.ledger.entries[0] is Income)
        assertEquals(1500.0, state.ledger.entries[0].amount, 0.001)
    }

    @Test
    fun shouldAddExpenseEntryAndUpdateState() = runTest {
        val viewModel = LedgerViewModel(InMemoryLedgerRepository())
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.addExpense(200.0, LocalDate.now(), "Groceries")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value as LedgerUiState.Success
        assertEquals(1, state.ledger.entries.size)
        assertTrue(state.ledger.entries[0] is IncurredExpense)
        assertEquals(200.0, state.ledger.entries[0].amount, 0.001)
        assertEquals("Groceries", state.ledger.entries[0].comments)
    }

    private class FailingLedgerRepository : LedgerRepository {
        override fun getLedger(): Ledger = throw RuntimeException("Storage error")
        override fun addEntry(entry: BookkeepingEntry) = throw RuntimeException("Storage error")
    }
}
