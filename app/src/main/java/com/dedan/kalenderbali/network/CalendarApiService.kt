package com.dedan.kalenderbali.network

import com.dedan.kalenderbali.model.DateEvent
import com.dedan.kalenderbali.model.EventDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CalendarApiService {
    @GET("api/v1/calendar")
    suspend fun getDates(
        @Query("month") month: Int,
        @Query("year") year: Int
    ): List<DateEvent>

    @GET("api/v1/calendar/{date}")
    suspend fun getEvents(@Path("date") date: String): List<EventDetail>
}