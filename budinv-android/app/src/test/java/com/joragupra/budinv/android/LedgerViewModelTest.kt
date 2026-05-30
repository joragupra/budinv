package com.joragupra.budinv.android

import app.cash.turbine.test
import com.joragupra.budinv.android.api.BookkeepingEntryDto
import com.joragupra.budinv.android.api.LedgerApi
import com.joragupra.budinv.android.api.LedgerDto
import com.joragupra.budinv.android.ui.LedgerUiState
import com.joragupra.budinv.android.ui.LedgerViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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
    private lateinit var api: LedgerApi

    private val aLedger = LedgerDto(
        from = "2026-01-01",
        to = "2026-01-31",
        entries = listOf(
            BookkeepingEntryDto.Income(id = 1, logDate = "2026-01-10", incurredDate = "2026-01-10", amount = 3000.0, comments = null),
        ),
        totalIncome = 3000.0,
        totalExpense = 0.0,
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        api = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun shouldEmitLoadingThenSuccessWhenApiSucceeds() = runTest {
        coEvery { api.getLedger() } returns aLedger

        val viewModel = LedgerViewModel(api)

        viewModel.uiState.test {
            assertTrue(awaitItem() is LedgerUiState.Loading)
            val success = awaitItem() as LedgerUiState.Success
            assertEquals(3000.0, success.ledger.balance, 0.001)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldEmitLoadingThenErrorWhenApiFails() = runTest {
        coEvery { api.getLedger() } throws RuntimeException("Connection refused")

        val viewModel = LedgerViewModel(api)

        viewModel.uiState.test {
            assertTrue(awaitItem() is LedgerUiState.Loading)
            val error = awaitItem() as LedgerUiState.Error
            assertEquals("Unable to load budget data. Please try again.", error.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldReloadOnRetry() = runTest {
        coEvery { api.getLedger() } returns aLedger

        val viewModel = LedgerViewModel(api)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            awaitItem() // current Success state

            viewModel.loadLedger()

            assertTrue(awaitItem() is LedgerUiState.Loading)
            val success = awaitItem() as LedgerUiState.Success
            assertEquals(aLedger, success.ledger)
            cancelAndIgnoreRemainingEvents()
        }
        coVerify(exactly = 2) { api.getLedger() }
    }

    @Test
    fun shouldCancelPreviousJobOnRapidReload() = runTest {
        coEvery { api.getLedger() } returns aLedger

        val viewModel = LedgerViewModel(api)
        viewModel.loadLedger() // second call before first coroutine runs

        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { api.getLedger() }
        assertTrue(viewModel.uiState.value is LedgerUiState.Success)
    }
}
