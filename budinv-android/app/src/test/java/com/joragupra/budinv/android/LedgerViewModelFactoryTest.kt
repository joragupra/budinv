package com.joragupra.budinv.android

import androidx.lifecycle.ViewModel
import com.joragupra.budinv.android.ui.LedgerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LedgerViewModelFactoryTest {

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
    fun shouldCreateLedgerViewModelForMatchingClass() {
        val result = LedgerViewModelFactory().create(LedgerViewModel::class.java)
        assertTrue(result is LedgerViewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowForUnknownViewModelClass() {
        LedgerViewModelFactory().create(UnknownViewModel::class.java)
    }

    private class UnknownViewModel : ViewModel()
}
