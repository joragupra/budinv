package com.joragupra.budinv.android

import com.joragupra.budinv.android.domain.LedgerRepository
import com.joragupra.budinv.domain.BookkeepingEntry
import com.joragupra.budinv.domain.Ledger
import java.time.LocalDate

class FakeLedgerRepository(
    private val shouldFail: Boolean = false
) : LedgerRepository {
    private val entries = mutableListOf<BookkeepingEntry>()

    override fun getLedger(): Ledger {
        if (shouldFail) throw RuntimeException("Fake failure")
        val ledger = Ledger(LocalDate.now().withDayOfMonth(1), LocalDate.now())
        entries.forEach { ledger.bookEntry(it) }
        return ledger
    }

    override fun addEntry(entry: BookkeepingEntry) {
        if (shouldFail) throw RuntimeException("Fake failure")
        entries.add(entry)
    }
}
