package com.joragupra.budinv.android.domain

import com.joragupra.budinv.domain.Ledger
import java.time.LocalDate

class InMemoryLedgerRepository : LedgerRepository {
    override fun getLedger(): Ledger {
        val now = LocalDate.now()
        return Ledger(now.withDayOfMonth(1), now)
    }
}
