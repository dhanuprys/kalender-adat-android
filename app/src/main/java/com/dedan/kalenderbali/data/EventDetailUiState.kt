package com.dedan.kalenderbali.data

import com.dedan.kalenderbali.model.EventDetail

sealed interface EventDetailUiState {
    object Loading : EventDetailUiState
    data class Success(val data: List<EventDetail>) : EventDetailUiState
    data class Error(val message: String) : EventDetailUiState
}