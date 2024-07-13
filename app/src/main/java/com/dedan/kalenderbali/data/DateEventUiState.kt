package com.dedan.kalenderbali.data

import com.dedan.kalenderbali.model.DateEvent

sealed interface DateEventUiState {
    object Loading : DateEventUiState
    data class Success(val data: List<DateEvent>) : DateEventUiState
    data class Error(val message: String) : DateEventUiState
}