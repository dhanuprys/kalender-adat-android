package com.dedan.kalenderadat.data

import com.dedan.kalenderadat.model.EventDetail

sealed interface EventDetailUiState {
    object Loading : EventDetailUiState
    data class Success(val data: List<EventDetail>) : EventDetailUiState
    data class Error(val message: String) : EventDetailUiState
}