package com.dedan.kalenderadat.data

import com.dedan.kalenderadat.model.EventDetail
import com.dedan.kalenderadat.model.PurtimDetail

sealed interface PurtimUiState {
    object Loading : PurtimUiState
    data class Success(val data: List<PurtimDetail>) : PurtimUiState
    data class Error(val message: String) : PurtimUiState
}