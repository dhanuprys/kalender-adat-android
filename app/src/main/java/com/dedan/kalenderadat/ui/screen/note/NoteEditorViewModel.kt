package com.dedan.kalenderadat.ui.screen.note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedan.kalenderadat.data.NoteItem
import com.dedan.kalenderadat.data.NoteRepository
import com.dedan.kalenderadat.util.DateUtil
import com.dedan.kalenderadat.util.safeSlice
import com.dedan.kalenderadat.util.translateDayIndexToBalineseDay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatterBuilder

class NoteEditorViewModel(
    savedStateHandle: SavedStateHandle,
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val date: String = checkNotNull(savedStateHandle[NoteEditorDestination.dateArg])

    var dateString: String
        private set

    var uiState: NoteEditorUiState by mutableStateOf(NoteEditorUiState())
        private set

    init {
        DateUtil.parseNormalizedDate(date).let {
            dateString = translateDayIndexToBalineseDay(it.dayOfWeek.value) + it.format(
                DateTimeFormatterBuilder().appendPattern(", dd MMMM yyyy").toFormatter()
            )
        }

        getNote()
    }

    suspend fun saveNote() {
        val noteContent = validateNoteContent(uiState.note!!)

        if (noteContent.isBlank()) {
            noteRepository.deleteNote(uiState.note!!)
            return
        }

        noteRepository.writeNote(uiState.note!!.copy(
            content = noteContent
        ))
    }

    fun updateNoteContent(content: String) {
        uiState = uiState.copy(
            note = uiState.note?.copy(
                content = content
            )
        )
    }

    private fun validateNoteContent(note: NoteItem): String =
        note.content.trim().safeSlice(0..500)

    private fun getNote() {
        viewModelScope.launch {
            uiState = NoteEditorUiState(
                note = noteRepository.getNoteByDate(date).first() ?: NoteItem(
                    date = date,
                    content = ""
                )
            )
        }
    }
}

data class NoteEditorUiState(
    val note: NoteItem? = null
)