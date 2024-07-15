package com.dedan.kalenderadat.data

import com.dedan.kalenderadat.model.DateEvent
import com.dedan.kalenderadat.model.EventDetail
import com.dedan.kalenderadat.model.HolidayDetail
import com.dedan.kalenderadat.model.PurtimDetail
import com.dedan.kalenderadat.network.CalendarApiService

interface CalendarRepository {
    suspend fun getDates(month: Int, year: Int): List<DateEvent>
    suspend fun getEvents(date: String): List<EventDetail>
    suspend fun getPurtim(month: Int, year: Int): List<PurtimDetail>
    suspend fun getHolidays(month: Int, year: Int): List<HolidayDetail>
}

class NetworkCalendarRepository(
    private val apiService: CalendarApiService
): CalendarRepository {
    override suspend fun getDates(month: Int, year: Int): List<DateEvent> {
        return apiService.getDates(month, year)
    }

    override suspend fun getEvents(date: String): List<EventDetail> {
        return apiService.getEvents(date)
    }

    override suspend fun getPurtim(month: Int, year: Int): List<PurtimDetail> {
        return apiService.getPurtim(month, year)
    }

    override suspend fun getHolidays(month: Int, year: Int): List<HolidayDetail> {
        return apiService.getHolidays(month, year)
    }
}