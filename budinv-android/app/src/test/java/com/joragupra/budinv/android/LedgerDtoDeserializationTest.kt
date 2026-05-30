package com.joragupra.budinv.android

import com.joragupra.budinv.android.api.BookkeepingEntryDto
import com.joragupra.budinv.android.api.LedgerDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LedgerDtoDeserializationTest {

    private lateinit var moshi: Moshi

    @Before
    fun setUp() {
        moshi = Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(BookkeepingEntryDto::class.java, "entryType")
                    .withSubtype(BookkeepingEntryDto.Income::class.java, "INCOME")
                    .withSubtype(BookkeepingEntryDto.IncurredExpense::class.java, "EXPENSE")
            )
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Test
    fun shouldDeserializeLedgerWithMixedEntries() {
        val json = """
            {
              "from": "2026-01-01",
              "to": "2026-01-31",
              "entries": [
                {
                  "id": 1,
                  "logDate": "2026-01-10",
                  "incurredDate": "2026-01-10",
                  "amount": 3000.0,
                  "comments": null,
                  "entryType": "INCOME"
                },
                {
                  "id": 2,
                  "logDate": "2026-01-15",
                  "incurredDate": "2026-01-15",
                  "amount": 800.0,
                  "comments": "Rent",
                  "entryType": "EXPENSE"
                }
              ],
              "totalIncome": 3000.0,
              "totalExpense": 800.0,
              "balance": 2200.0
            }
        """.trimIndent()

        val adapter = moshi.adapter(LedgerDto::class.java)
        val ledger = adapter.fromJson(json)!!

        assertEquals("2026-01-01", ledger.from)
        assertEquals("2026-01-31", ledger.to)
        assertEquals(3000.0, ledger.totalIncome, 0.001)
        assertEquals(800.0, ledger.totalExpense, 0.001)
        assertEquals(2200.0, ledger.balance, 0.001)
        assertEquals(2, ledger.entries.size)

        assertTrue(ledger.entries[0] is BookkeepingEntryDto.Income)
        assertTrue(ledger.entries[1] is BookkeepingEntryDto.IncurredExpense)

        val expense = ledger.entries[1] as BookkeepingEntryDto.IncurredExpense
        assertEquals("Rent", expense.comments)
        assertEquals(800.0, expense.amount, 0.001)
    }

    @Test
    fun shouldDeserializeEmptyLedger() {
        val json = """
            {
              "from": "2026-01-01",
              "to": "2026-01-31",
              "entries": [],
              "totalIncome": 0.0,
              "totalExpense": 0.0,
              "balance": 0.0
            }
        """.trimIndent()

        val adapter = moshi.adapter(LedgerDto::class.java)
        val ledger = adapter.fromJson(json)!!

        assertTrue(ledger.entries.isEmpty())
        assertEquals(0.0, ledger.balance, 0.001)
    }

    @Test
    fun shouldDeserializeEntryWithNullComments() {
        val json = """
            {
              "from": "2026-01-01",
              "to": "2026-01-31",
              "entries": [
                {
                  "id": 1,
                  "logDate": "2026-01-10",
                  "incurredDate": "2026-01-10",
                  "amount": 3000.0,
                  "comments": null,
                  "entryType": "INCOME"
                }
              ],
              "totalIncome": 3000.0,
              "totalExpense": 0.0,
              "balance": 3000.0
            }
        """.trimIndent()

        val adapter = moshi.adapter(LedgerDto::class.java)
        val ledger = adapter.fromJson(json)!!

        val income = ledger.entries[0] as BookkeepingEntryDto.Income
        assertEquals(null, income.comments)
    }
}
