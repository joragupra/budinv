package com.joragupra.budinv.android.api

import retrofit2.http.GET

interface LedgerApi {

    @GET("ledger/show/list")
    suspend fun getLedger(): LedgerDto
}
