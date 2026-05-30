package com.joragupra.budinv.android.domain

import com.joragupra.budinv.domain.Ledger

interface LedgerRepository {
    fun getLedger(): Ledger
}
