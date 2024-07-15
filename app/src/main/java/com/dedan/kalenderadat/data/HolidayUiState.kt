package com.dedan.kalenderadat.data

import com.dedan.kalenderadat.model.EventDetail
import com.dedan.kalenderadat.model.HolidayDetail
import com.dedan.kalenderadat.model.PurtimDetail

sealed interface HolidayUiState {
    object Loading : HolidayUiState
    data class Success(val data: List<HolidayDetail>) : HolidayUiState
    data class Error(val message: String) : HolidayUiState
}