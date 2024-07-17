package com.dedan.kalenderadat.ui.screen.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedan.kalenderadat.data.CalendarRepository
import com.dedan.kalenderadat.data.CalendarUiState
import com.dedan.kalenderadat.data.DateEventUiState
import com.dedan.kalenderadat.data.EventDetailUiState
import com.dedan.kalenderadat.data.HolidayUiState
import com.dedan.kalenderadat.data.NoteItemUiState
import com.dedan.kalenderadat.data.NoteRepository
import com.dedan.kalenderadat.data.PurtimUiState
import com.dedan.kalenderadat.util.DateUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeViewModel(
    private val calendarRepository: CalendarRepository,
    private val noteRepository: NoteRepository
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
    var noteState: NoteItemUiState by mutableStateOf(NoteItemUiState())
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
                fetchNote(date)
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
                    dates = DateUtil.calculateSortedDates(currentDate),
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
            dates = DateUtil.calculateSortedDates(LocalDate.now())
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

    fun fetchNote(currentDate: LocalDate) {
        viewModelScope.launch {
            noteRepository.getNoteByDate(
                currentDate.format(
                    DateUtil.normalizeDateFormat()
                )
            ).collect {
                noteState = NoteItemUiState(
                    available = it != null,
                    note = it
                )
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
                        DateUtil.normalizeDateFormat()
                    )
                )
                EventDetailUiState.Success(events)
            } catch (e: Exception) {
                Log.d("ViewModel Date", e.message ?: "Error bro")
                EventDetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}