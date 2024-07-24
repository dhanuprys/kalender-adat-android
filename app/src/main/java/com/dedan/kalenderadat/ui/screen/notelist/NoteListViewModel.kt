package com.dedan.kalenderadat.ui.screen.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedan.kalenderadat.data.NoteItem
import com.dedan.kalenderadat.data.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class NoteListViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {
    val notes = noteRepository.getAllNotes()
        .map { NoteListUiState(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            NoteListUiState()
        )
}

data class NoteListUiState(
    val notes: List<NoteItem> = emptyList()
)