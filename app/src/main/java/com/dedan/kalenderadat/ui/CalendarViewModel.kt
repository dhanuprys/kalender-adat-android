package com.dedan.kalenderadat.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dedan.kalenderadat.data.CalendarRepository
import com.dedan.kalenderadat.data.CalendarUiState
import com.dedan.kalenderadat.data.DateEventUiState
import com.dedan.kalenderadat.data.DefaultAppContainer
import com.dedan.kalenderadat.data.EventDetailUiState
import com.dedan.kalenderadat.data.HolidayUiState
import com.dedan.kalenderadat.data.PurtimUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder

class CalendarViewModel(
    private val calendarRepository: CalendarRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<CalendarUiState> = MutableStateFlow(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    var dateEventUiState: DateEventUiState by mutableStateOf(DateEventUiState.Loading)
        private set
    var eventDetailUiState: EventDetailUiState by mutableStateOf(EventDetailUiState.Loading)
        private set
    var purtimUiState: PurtimUiState by mutableStateOf(PurtimUiState.Loading)
        private set
    var holidayUiState: HolidayUiState by mutableStateOf(HolidayUiState.Loading)
        private set

    init {
        resetCalendar()
    }

    fun setOpenDetailDate(date: LocalDate) {
        viewModelScope.launch {
            // double click
            if (_uiState.value.selectedDate == date) {
                setBottomSheetExpand(true)
            } else {
                _uiState.update {
                    it.copy(
                        selectedDate = date
                    )
                }

                fetchDateDetail(date)
            }
        }
    }

    fun setCurrentDate(currentDate: LocalDate) {
        viewModelScope.launch {
            eventDetailUiState = EventDetailUiState.Loading
            _uiState.update {
                it.copy(
                    selectedDate = null,
                    currentDate = currentDate,
                    dates = calculateSortedDates(currentDate),
                    bottomSheetExpand = false
                )
            }

            fetchDates(currentDate)
            fetchPurtim(currentDate)
            fetchHolidays(currentDate)
        }
    }

    fun resetCalendar() {
        _uiState.value = CalendarUiState(
            dates = calculateSortedDates(LocalDate.now())
        )

        setCurrentDate(_uiState.value.currentDate)
    }

    fun setBottomSheetExpand(expand: Boolean) {
        _uiState.update {
            it.copy(
                bottomSheetExpand = expand
            )
        }
    }

    fun fetchDates(currentDate: LocalDate) {
        viewModelScope.launch {
            dateEventUiState = DateEventUiState.Loading
            dateEventUiState = try {
                val dates = calendarRepository.getDates(
                    month = currentDate.monthValue,
                    year = currentDate.year
                )
                DateEventUiState.Success(dates)
            } catch (e: Exception) {
                DateEventUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchPurtim(currentDate: LocalDate) {
        viewModelScope.launch {
            Log.d("ViewModel Purtim", "Start request purtim")
            purtimUiState = PurtimUiState.Loading
            purtimUiState = try {
                val dates = calendarRepository.getPurtim(
                    month = currentDate.monthValue,
                    year = currentDate.year
                )
                Log.d("ViewModel Purtim", "Success request purtim")
                PurtimUiState.Success(dates)
            } catch (e: Exception) {
                Log.d("ViewModel Purtim", e.message ?: "Error bro")
                PurtimUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchHolidays(currentDate: LocalDate) {
        viewModelScope.launch {
            holidayUiState = HolidayUiState.Loading
            holidayUiState = try {
                val holidays = calendarRepository.getHolidays(
                    month = currentDate.monthValue,
                    year = currentDate.year
                )
                HolidayUiState.Success(holidays)
            } catch (e: Exception) {
                HolidayUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchDateDetail(date: LocalDate) {
        viewModelScope.launch {
            eventDetailUiState = EventDetailUiState.Loading
            eventDetailUiState = try {
                // TODO("CHANGE HERE")
                val events = calendarRepository.getEvents(
                    date.format(
                        DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").toFormatter()
                    )
                )
                EventDetailUiState.Success(events)
            } catch (e: Exception) {
                Log.d("ViewModel Date", e.message ?: "Error bro")
                EventDetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun calculateSortedDates(currentDate: LocalDate): List<LocalDate> {
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val container = DefaultAppContainer()
                CalendarViewModel(container.calendarRepository)
            }
        }
    }
}