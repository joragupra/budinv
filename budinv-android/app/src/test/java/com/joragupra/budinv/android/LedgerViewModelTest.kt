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
import kotlinx.coroutines.flow.SharedFlow
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
        val repository = CountingLedgerRepository()
        val viewModel = LedgerViewModel(repository)
        viewModel.loadLedger() // second call before first coroutine runs

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, repository.getLedgerCallCount)
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

    @Test
    fun shouldKeepSuccessStateWhenAddExpenseFails() = runTest {
        val viewModel = LedgerViewModel(AddEntryFailingLedgerRepository())
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.addExpense(200.0, LocalDate.now(), "Groceries")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is LedgerUiState.Success)
    }

    @Test
    fun shouldEmitSaveErrorWhenAddExpenseFails() = runTest {
        val viewModel = LedgerViewModel(AddEntryFailingLedgerRepository())
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.saveError.test {
            viewModel.addExpense(200.0, LocalDate.now(), "Groceries")
            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals("Failed to save expense entry. Please try again.", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldKeepSuccessStateWhenAddIncomeFails() = runTest {
        val viewModel = LedgerViewModel(AddEntryFailingLedgerRepository())
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.addIncome(1500.0, LocalDate.now(), null)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is LedgerUiState.Success)
    }

    @Test
    fun shouldEmitSaveErrorWhenAddIncomeFails() = runTest {
        val viewModel = LedgerViewModel(AddEntryFailingLedgerRepository())
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.saveError.test {
            viewModel.addIncome(1500.0, LocalDate.now(), null)
            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals("Failed to save income entry. Please try again.", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldEmitReloadErrorWhenGetLedgerFailsAfterSuccessfulIncomeSave() = runTest {
        val viewModel = LedgerViewModel(ReloadAfterSaveFailingLedgerRepository())
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.saveError.test {
            viewModel.addIncome(1500.0, LocalDate.now(), null)
            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals("Entry saved. Failed to reload updated data.", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldEmitReloadErrorWhenGetLedgerFailsAfterSuccessfulExpenseSave() = runTest {
        val viewModel = LedgerViewModel(ReloadAfterSaveFailingLedgerRepository())
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.saveError.test {
            viewModel.addExpense(200.0, LocalDate.now(), "Groceries")
            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals("Entry saved. Failed to reload updated data.", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldIsolateSuccessLedgerFromRepositoryReference() = runTest {
        val repository = DirectReferenceLedgerRepository()
        val viewModel = LedgerViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value as LedgerUiState.Success

        repository.internalLedger.bookEntry(Income().apply {
            setAmount(999.0)
            setIncurredDate(LocalDate.now())
            setLogDate(LocalDate.now())
        })

        assertEquals(0, state.ledger.entries.size)
    }

    @Test
    fun shouldRecoverFromErrorStateWhenLoadLedgerIsCalledAgain() = runTest {
        val repository = RecoveringLedgerRepository()
        val viewModel = LedgerViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(viewModel.uiState.value is LedgerUiState.Error)

        repository.recover()
        viewModel.loadLedger()
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is LedgerUiState.Success)
    }

    private class RecoveringLedgerRepository : LedgerRepository {
        private var failing = true
        fun recover() { failing = false }
        override fun getLedger(): Ledger {
            if (failing) throw RuntimeException("Temporary failure")
            return InMemoryLedgerRepository().getLedger()
        }
        override fun addEntry(entry: BookkeepingEntry) = Unit
    }

    private class CountingLedgerRepository : LedgerRepository {
        var getLedgerCallCount = 0
            private set
        private val delegate = InMemoryLedgerRepository()
        override fun getLedger(): Ledger { getLedgerCallCount++; return delegate.getLedger() }
        override fun addEntry(entry: BookkeepingEntry) = delegate.addEntry(entry)
    }

    private class DirectReferenceLedgerRepository : LedgerRepository {
        val internalLedger = Ledger(LocalDate.now().withDayOfMonth(1), LocalDate.now())
        override fun getLedger(): Ledger = internalLedger
        override fun addEntry(entry: BookkeepingEntry) = Unit
    }

    private class FailingLedgerRepository : LedgerRepository {
        override fun getLedger(): Ledger = throw RuntimeException("Storage error")
        override fun addEntry(entry: BookkeepingEntry) = throw RuntimeException("Storage error")
    }

    private class AddEntryFailingLedgerRepository : LedgerRepository {
        private val delegate = InMemoryLedgerRepository()
        override fun getLedger(): Ledger = delegate.getLedger()
        override fun addEntry(entry: BookkeepingEntry) = throw RuntimeException("Storage error")
    }

    private class ReloadAfterSaveFailingLedgerRepository : LedgerRepository {
        private val delegate = InMemoryLedgerRepository()
        private var getLedgerCallCount = 0

        override fun getLedger(): Ledger {
            if (++getLedgerCallCount > 1) throw RuntimeException("Reload failed")
            return delegate.getLedger()
        }

        override fun addEntry(entry: BookkeepingEntry) = delegate.addEntry(entry)
    }
}
