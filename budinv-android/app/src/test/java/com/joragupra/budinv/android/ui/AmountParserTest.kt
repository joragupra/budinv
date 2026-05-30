package com.joragupra.budinv.android.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AmountParserTest {

    @Test
    fun shouldParseEnglishDecimalFormat() {
        assertEquals(1234.56, parseAmount("1234.56")!!, 0.001)
    }

    @Test
    fun shouldParseEnglishFormatWithThousandsSeparator() {
        assertEquals(1234.56, parseAmount("1,234.56")!!, 0.001)
    }

    @Test
    fun shouldParseEuropeanDecimalFormat() {
        assertEquals(1234.56, parseAmount("1234,56")!!, 0.001)
    }

    @Test
    fun shouldParseEuropeanFormatWithThousandsSeparator() {
        assertEquals(1234.56, parseAmount("1.234,56")!!, 0.001)
    }

    @Test
    fun shouldParseWholeNumber() {
        assertEquals(100.0, parseAmount("100")!!, 0.001)
    }

    @Test
    fun shouldReturnNullForZero() {
        assertNull(parseAmount("0"))
    }

    @Test
    fun shouldReturnNullForNegativeAmount() {
        assertNull(parseAmount("-50"))
    }

    @Test
    fun shouldReturnNullForNonNumericInput() {
        assertNull(parseAmount("abc"))
    }

    @Test
    fun shouldReturnNullForBlankInput() {
        assertNull(parseAmount(""))
    }

    @Test
    fun shouldReturnNullForMultipleDecimalPoints() {
        assertNull(parseAmount("1.234.56"))
    }

    @Test
    fun shouldReturnNullForInfiniteValue() {
        assertNull(parseAmount("Infinity"))
    }
}
