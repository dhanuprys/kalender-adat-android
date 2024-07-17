package com.dedan.kalenderadat.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

object DateUtil {
    fun normalizeDateFormat(): DateTimeFormatter =
        DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").toFormatter()

    fun parseNormalizedDate(date: String): LocalDate =
        LocalDate.parse(
            date,
            normalizeDateFormat()
        )


    fun calculateSortedDates(currentDate: LocalDate): List<LocalDate> {
        val sortedDates: MutableList<LocalDate> = mutableListOf()

        val firstDay = currentDate.withDayOfMonth(1)
        val totalDay = currentDate.lengthOfMonth()
        var prevMonthStart: LocalDate? = null
        val nextMonthStart: LocalDate = firstDay.plusMonths(1)

        var preDay = 0
        var nextMonthLimit = 0

        if (firstDay.dayOfWeek.value < 7) {
            preDay = firstDay.dayOfWeek.value
            prevMonthStart = firstDay.minusDays(preDay.toLong())
        }

        nextMonthLimit = 42 - (preDay + totalDay)

        if (prevMonthStart != null) {
            val prevMonthStartDate = prevMonthStart.dayOfMonth
            val prevMonthEndDate = prevMonthStart.lengthOfMonth()

            for (i in prevMonthStartDate..prevMonthEndDate) {
                sortedDates.add(
                    prevMonthStart.withDayOfMonth(i)
                )
            }
        }

        for (i in 1..totalDay) {
            sortedDates.add(
                currentDate.withDayOfMonth(i)
            )
        }

        for (i in 1..nextMonthLimit) {
            sortedDates.add(
                nextMonthStart.withDayOfMonth(i)
            )
        }

        return sortedDates
    }
}