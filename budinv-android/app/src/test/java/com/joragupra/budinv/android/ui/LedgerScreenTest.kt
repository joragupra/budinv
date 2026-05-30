package com.joragupra.budinv.android.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.joragupra.budinv.android.domain.LedgerRepository
import com.joragupra.budinv.domain.BookkeepingEntry
import com.joragupra.budinv.domain.ExpenseConcept
import com.joragupra.budinv.domain.Income
import com.joragupra.budinv.domain.IncurredExpense
import com.joragupra.budinv.domain.Ledger
import java.time.LocalDate
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class LedgerScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // ── Top bar ──────────────────────────────────────────────────────────────

    @Test
    fun shouldShowAppTitleInTopBar() {
        composeTestRule.setContent { LedgerScreen(viewModel()) }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("BudInv Ledger").assertIsDisplayed()
    }

    // ── Success: summary card ─────────────────────────────────────────────────

    @Test
    fun shouldShowSummaryCardLabelsForEmptyLedger() {
        composeTestRule.setContent { LedgerScreen(viewModel()) }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Income").assertIsDisplayed()
        composeTestRule.onNodeWithText("Expenses").assertIsDisplayed()
        composeTestRule.onNodeWithText("Balance").assertIsDisplayed()
    }

    @Test
    fun shouldShowFormattedIncomeInSummaryCard() {
        composeTestRule.setContent { LedgerScreen(viewModel(incomeEntry(150.0))) }
        composeTestRule.waitForIdle()
        // "150.00" appears in income row, balance row, and entry row — take the first
        composeTestRule.onAllNodesWithText("150.00")[0].assertIsDisplayed()
    }

    @Test
    fun shouldShowFormattedExpenseInSummaryCard() {
        composeTestRule.setContent { LedgerScreen(viewModel(expenseEntry(75.50))) }
        composeTestRule.waitForIdle()
        // "75.50" appears in expense row and entry row (balance is "-75.50") — take the first
        composeTestRule.onAllNodesWithText("75.50")[0].assertIsDisplayed()
    }

    // ── Success: entry rows ───────────────────────────────────────────────────

    @Test
    fun shouldShowIncomeRowForIncomeEntry() {
        composeTestRule.setContent { LedgerScreen(viewModel(incomeEntry(100.0))) }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("INCOME").assertIsDisplayed()
    }

    @Test
    fun shouldShowExpenseRowForExpenseEntry() {
        composeTestRule.setContent { LedgerScreen(viewModel(expenseEntry(200.0))) }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("EXPENSE").assertIsDisplayed()
    }

    @Test
    fun shouldShowCommentsWhenEntryHasComments() {
        composeTestRule.setContent { LedgerScreen(viewModel(expenseEntry(50.0, "Groceries"))) }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Groceries").assertIsDisplayed()
    }

    @Test
    fun shouldShowIncurredDateForEntry() {
        composeTestRule.setContent { LedgerScreen(viewModel(incomeEntry(100.0))) }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText(LocalDate.now().toString()).assertIsDisplayed()
    }

    // ── Error state ───────────────────────────────────────────────────────────

    @Test
    fun shouldShowErrorMessageWhenLoadFails() {
        composeTestRule.setContent { LedgerScreen(viewModel(repository = AlwaysFailingRepository())) }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Error: Unable to load budget data. Please try again.").assertIsDisplayed()
    }

    @Test
    fun shouldShowRetryButtonInErrorState() {
        composeTestRule.setContent { LedgerScreen(viewModel(repository = AlwaysFailingRepository())) }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }

    @Test
    fun shouldReloadWhenRetryIsClicked() {
        composeTestRule.setContent { LedgerScreen(viewModel(repository = AlwaysFailingRepository())) }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Retry").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Error: Unable to load budget data. Please try again.").assertIsDisplayed()
    }

    // ── FAB → AddEntrySheet ───────────────────────────────────────────────────

    @Test
    fun shouldShowAddEntrySheetWhenFabIsClicked() {
        composeTestRule.setContent { LedgerScreen(viewModel()) }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("Add entry").performClick()
        composeTestRule.onNodeWithText("Add Entry").assertIsDisplayed()
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private fun viewModel(
        vararg entries: BookkeepingEntry,
        repository: LedgerRepository = PreloadedRepository(*entries),
    ) = LedgerViewModel(repository)

    private fun incomeEntry(amount: Double, comments: String? = null) =
        Income().apply {
            setAmount(amount)
            setIncurredDate(LocalDate.now())
            setLogDate(LocalDate.now())
            setComments(comments)
        }

    private fun expenseEntry(amount: Double, comments: String? = null) =
        IncurredExpense(ExpenseConcept("Miscellaneous")).apply {
            setAmount(amount)
            setIncurredDate(LocalDate.now())
            setLogDate(LocalDate.now())
            setComments(comments)
        }

    private class PreloadedRepository(vararg entries: BookkeepingEntry) : LedgerRepository {
        private val stored = entries.toMutableList()
        override fun getLedger(): Ledger {
            val ledger = Ledger(LocalDate.now().withDayOfMonth(1), LocalDate.now())
            stored.forEach { ledger.bookEntry(it) }
            return ledger
        }
        override fun addEntry(entry: BookkeepingEntry) { stored.add(entry) }
    }

    private class AlwaysFailingRepository : LedgerRepository {
        override fun getLedger(): Ledger = throw RuntimeException("Network error")
        override fun addEntry(entry: BookkeepingEntry) = throw RuntimeException("Network error")
    }
}
