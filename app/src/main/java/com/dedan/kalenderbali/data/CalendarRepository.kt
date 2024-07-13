package com.dedan.kalenderbali.data

import com.dedan.kalenderbali.model.DateEvent
import com.dedan.kalenderbali.model.EventDetail
import com.dedan.kalenderbali.network.CalendarApiService

interface CalendarRepository {
    suspend fun getDates(month: Int, year: Int): List<DateEvent>
    suspend fun getEvents(date: String): List<EventDetail>
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
}