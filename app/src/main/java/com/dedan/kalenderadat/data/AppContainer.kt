package com.dedan.kalenderadat.data

import com.dedan.kalenderadat.network.CalendarApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val calendarRepository: CalendarRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://adat.suryamahendra.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private val retrofitService by lazy {
        retrofit.create(CalendarApiService::class.java)
    }

    override val calendarRepository: CalendarRepository by lazy {
        NetworkCalendarRepository(retrofitService)
    }
}