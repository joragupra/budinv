package com.joragupra.budinv.android.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class AddEntrySheetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplayIncomeAndExpenseTypeOptions() {
        composeTestRule.setContent {
            AddEntryForm(onDismiss = {}, onConfirm = { _, _, _ -> })
        }
        composeTestRule.onNodeWithText("Income").assertIsDisplayed()
        composeTestRule.onNodeWithText("Expense").assertIsDisplayed()
    }

    @Test
    fun shouldDisableSaveButtonWhenAmountIsEmpty() {
        composeTestRule.setContent {
            AddEntryForm(onDismiss = {}, onConfirm = { _, _, _ -> })
        }
        composeTestRule.onNodeWithText("Save").assertIsNotEnabled()
    }

    @Test
    fun shouldEnableSaveButtonWhenValidAmountIsEntered() {
        composeTestRule.setContent {
            AddEntryForm(onDismiss = {}, onConfirm = { _, _, _ -> })
        }
        composeTestRule.onNodeWithText("Amount").performTextInput("100")
        composeTestRule.onNodeWithText("Save").assertIsEnabled()
    }

    @Test
    fun shouldShowErrorMessageForInvalidAmount() {
        composeTestRule.setContent {
            AddEntryForm(onDismiss = {}, onConfirm = { _, _, _ -> })
        }
        composeTestRule.onNodeWithText("Amount").performTextInput("abc")
        composeTestRule.onNodeWithText("Enter a positive amount, e.g. 1234.56").assertIsDisplayed()
    }

    @Test
    fun shouldCallOnDismissWhenCancelClicked() {
        var dismissed = false
        composeTestRule.setContent {
            AddEntryForm(onDismiss = { dismissed = true }, onConfirm = { _, _, _ -> })
        }
        composeTestRule.onNodeWithText("Cancel").performClick()
        assertTrue(dismissed)
    }

    @Test
    fun shouldCallOnConfirmWithIncomeTypeAndNullCommentsWhenSaveClicked() {
        var capturedType: EntryType? = null
        var capturedAmount: Double? = null
        var capturedComments: String? = "sentinel"
        composeTestRule.setContent {
            AddEntryForm(
                onDismiss = {},
                onConfirm = { type, amount, comments ->
                    capturedType = type
                    capturedAmount = amount
                    capturedComments = comments
                }
            )
        }
        composeTestRule.onNodeWithText("Amount").performTextInput("100")
        composeTestRule.onNodeWithText("Save").performClick()
        assertEquals(EntryType.Income, capturedType)
        assertEquals(100.0, capturedAmount!!, 0.001)
        assertNull(capturedComments)
    }

    @Test
    fun shouldPassCommentsToOnConfirmWhenProvided() {
        var capturedComments: String? = null
        composeTestRule.setContent {
            AddEntryForm(
                onDismiss = {},
                onConfirm = { _, _, comments -> capturedComments = comments }
            )
        }
        composeTestRule.onNodeWithText("Amount").performTextInput("100")
        composeTestRule.onNodeWithText("Comments (optional)").performTextInput("Groceries")
        composeTestRule.onNodeWithText("Save").performClick()
        assertEquals("Groceries", capturedComments)
    }

    @Test
    fun shouldTreatCommaAsDecimalSeparatorInAmount() {
        var capturedAmount: Double? = null
        composeTestRule.setContent {
            AddEntryForm(
                onDismiss = {},
                onConfirm = { _, amount, _ -> capturedAmount = amount }
            )
        }
        composeTestRule.onNodeWithText("Amount").performTextInput("99,99")
        composeTestRule.onNodeWithText("Save").performClick()
        assertEquals(99.99, capturedAmount!!, 0.001)
    }

    @Test
    fun shouldSubmitExpenseTypeWhenExpenseChipIsSelected() {
        var capturedType: EntryType? = null
        composeTestRule.setContent {
            AddEntryForm(
                onDismiss = {},
                onConfirm = { type, _, _ -> capturedType = type }
            )
        }
        composeTestRule.onNodeWithText("Expense").performClick()
        composeTestRule.onNodeWithText("Amount").performTextInput("50")
        composeTestRule.onNodeWithText("Save").performClick()
        assertEquals(EntryType.Expense, capturedType)
    }
}
