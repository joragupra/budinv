package com.joragupra.budinv.android.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LedgerDto(
    val from: String,
    val to: String,
    val entries: List<BookkeepingEntryDto>,
    val totalIncome: Double,
    val totalExpense: Double,
    val balance: Double,
)

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
        @Json(name = "entryType") override val entryType: String = "INCOME",
    ) : BookkeepingEntryDto()

    @JsonClass(generateAdapter = true)
    data class IncurredExpense(
        override val id: Long?,
        override val logDate: String,
        override val incurredDate: String,
        override val amount: Double,
        override val comments: String?,
        @Json(name = "entryType") override val entryType: String = "EXPENSE",
    ) : BookkeepingEntryDto()
}
