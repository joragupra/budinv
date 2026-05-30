package com.joragupra.budinv.android.domain

import com.joragupra.budinv.domain.BookkeepingEntry
import com.joragupra.budinv.domain.Ledger
import java.time.LocalDate

class InMemoryLedgerRepository : LedgerRepository {
    private val now = LocalDate.now()
    private val ledger = Ledger(now.withDayOfMonth(1), now)

    override fun getLedger(): Ledger = ledger

    override fun addEntry(entry: BookkeepingEntry) {
        ledger.bookEntry(entry)
    }
}
