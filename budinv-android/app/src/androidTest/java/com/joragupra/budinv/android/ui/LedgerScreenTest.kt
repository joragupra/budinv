package com.joragupra.budinv.android.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.joragupra.budinv.android.FakeLedgerRepository
import com.joragupra.budinv.android.theme.BudInvTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LedgerScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var fakeRepository: FakeLedgerRepository
    private lateinit var viewModel: LedgerViewModel

    @Before
    fun setUp() {
        fakeRepository = FakeLedgerRepository()
        viewModel = LedgerViewModel(fakeRepository)
    }

    private fun launchScreen() {
        composeTestRule.setContent {
            BudInvTheme {
                LedgerScreen(viewModel)
            }
        }
    }

    // Test 1 — simplest baseline: the scaffold title is always visible
    @Test
    fun shouldDisplayLedgerTitle() {
        launchScreen()
        composeTestRule.onNodeWithText("BudInv Ledger").assertIsDisplayed()
    }

    // Test 2 — the add-entry trigger is present
    @Test
    fun shouldDisplayAddEntryFab() {
        launchScreen()
        composeTestRule.onNodeWithContentDescription("Add entry").assertIsDisplayed()
    }

    // Test 3 — error state: shown when repository fails
    @Test
    fun shouldShowRetryButtonWhenLoadFails() {
        fakeRepository = FakeLedgerRepository(shouldFail = true)
        viewModel = LedgerViewModel(fakeRepository)
        launchScreen()
        composeTestRule.waitUntil(5_000) {
            composeTestRule.onAllNodesWithText("Retry").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }

    // Test 4 — tapping FAB opens the add-entry bottom sheet
    @Test
    fun shouldOpenAddEntrySheetWhenFabIsClicked() {
        launchScreen()
        composeTestRule.onNodeWithContentDescription("Add entry").performClick()
        composeTestRule.onNodeWithText("Save").assertIsDisplayed()
    }

    // Test 5 — adding income: full flow produces an INCOME entry in the list
    @Test
    fun shouldDisplayIncomeEntryAfterAddingIncome() {
        launchScreen()
        composeTestRule.onNodeWithContentDescription("Add entry").performClick()
        // Income chip is selected by default — no click needed
        composeTestRule.onNodeWithText("Amount").performTextInput("100")
        composeTestRule.onNodeWithText("Save").performClick()
        composeTestRule.waitUntil(5_000) {
            composeTestRule.onAllNodesWithText("INCOME").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("INCOME").assertIsDisplayed()
    }

    // Test 6 — adding expense: full flow produces an EXPENSE entry in the list
    @Test
    fun shouldDisplayExpenseEntryAfterAddingExpense() {
        launchScreen()
        composeTestRule.onNodeWithContentDescription("Add entry").performClick()
        composeTestRule.onNodeWithText("Expense").performClick()
        composeTestRule.onNodeWithText("Amount").performTextInput("50")
        composeTestRule.onNodeWithText("Save").performClick()
        composeTestRule.waitUntil(5_000) {
            composeTestRule.onAllNodesWithText("EXPENSE").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("EXPENSE").assertIsDisplayed()
    }

    // Test 7 — form validation: Save is disabled until a valid amount is typed
    @Test
    fun shouldDisableSaveButtonUntilValidAmountIsEntered() {
        launchScreen()
        composeTestRule.onNodeWithContentDescription("Add entry").performClick()
        composeTestRule.onNodeWithText("Save").assertIsNotEnabled()
        composeTestRule.onNodeWithText("Amount").performTextInput("100")
        composeTestRule.onNodeWithText("Save").assertIsEnabled()
    }
}
