package com.joragupra.budinv.android.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LedgerDto(
    val from: String,
    val to: String,
    val entries: List<BookkeepingEntryDto>,
    val totalIncome: Double,
    val totalExpense: Double,
) {
    val balance: Double get() = totalIncome - totalExpense
}

sealed class BookkeepingEntryDto {
    abstract val id: Long?
    abstract val logDate: String
    abstract val incurredDate: String
    abstract val amount: Double
    abstract val comments: String?
    abstract val entryType: String

    @JsonClass(generateAdapter = true)
    data class Income(
        override val id: Long?,
        override val logDate: String,
        override val incurredDate: String,
        override val amount: Double,
        override val comments: String?,
    ) : BookkeepingEntryDto() {
        override val entryType: String get() = "INCOME"
    }

    @JsonClass(generateAdapter = true)
    data class IncurredExpense(
        override val id: Long?,
        override val logDate: String,
        override val incurredDate: String,
        override val amount: Double,
        override val comments: String?,
    ) : BookkeepingEntryDto() {
        override val entryType: String get() = "EXPENSE"
    }
}
