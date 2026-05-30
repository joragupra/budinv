package com.joragupra.budinv.android.domain

import com.joragupra.budinv.domain.BookkeepingEntry
import com.joragupra.budinv.domain.Ledger
import java.time.LocalDate

class InMemoryLedgerRepository : LedgerRepository {
    private val from = LocalDate.now().withDayOfMonth(1)
    private val to = LocalDate.now()
    private val entries = mutableListOf<BookkeepingEntry>()

    override fun getLedger(): Ledger {
        val ledger = Ledger(from, to)
        entries.forEach { ledger.bookEntry(it) }
        return ledger
    }

    override fun addEntry(entry: BookkeepingEntry) {
        entries.add(entry)
    }
}
