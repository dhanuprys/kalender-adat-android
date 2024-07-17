package com.dedan.kalenderadat.data

import android.content.Context
import com.dedan.kalenderadat.network.CalendarApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val calendarRepository: CalendarRepository
    val noteRepository: NoteRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
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

    override val noteRepository: NoteRepository by lazy {
        LocalNoteRepository(
            NoteDatabase.getDatabase(context).noteDao()
        )
    }
}