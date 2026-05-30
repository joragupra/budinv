package com.joragupra.budinv.android.api

import com.joragupra.budinv.android.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    val ledgerApi: LedgerApi by lazy {
        val moshi = Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(BookkeepingEntryDto::class.java, "entryType")
                    .withSubtype(BookkeepingEntryDto.Income::class.java, "INCOME")
                    .withSubtype(BookkeepingEntryDto.IncurredExpense::class.java, "EXPENSE")
            )
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val okHttp = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                }
            )
            .build()

        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttp)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(LedgerApi::class.java)
    }
}
