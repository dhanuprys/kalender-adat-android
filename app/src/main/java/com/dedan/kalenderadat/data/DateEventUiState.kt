package com.dedan.kalenderadat.data

import com.dedan.kalenderadat.model.DateEvent

sealed interface DateEventUiState {
    object Loading : DateEventUiState
    data class Success(val data: List<DateEvent>) : DateEventUiState
    data class Error(val message: String) : DateEventUiState
}