package com.dedan.kalenderadat.data

import java.time.LocalDate

data class CalendarUiState(
    val selectedDate: LocalDate? = null,
    val currentDate: LocalDate = LocalDate.now(),
    val dates: List<LocalDate> = emptyList(),
    val bottomSheetExpand: Boolean = false
)