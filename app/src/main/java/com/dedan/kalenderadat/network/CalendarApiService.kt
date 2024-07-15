package com.dedan.kalenderadat.network

import com.dedan.kalenderadat.model.DateEvent
import com.dedan.kalenderadat.model.EventDetail
import com.dedan.kalenderadat.model.HolidayDetail
import com.dedan.kalenderadat.model.PurtimDetail
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

    @GET("api/v1/purtim")
    suspend fun getPurtim(
        @Query("month") month: Int,
        @Query("year") year: Int
    ): List<PurtimDetail>

    @GET("api/v1/holiday")
    suspend fun getHolidays(
        @Query("month") month: Int,
        @Query("year") year: Int
    ): List<HolidayDetail>
}