package com.dedan.kalenderadat

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dedan.kalenderadat.ui.screen.home.HomeViewModel
import com.dedan.kalenderadat.ui.screen.note.NoteEditorViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                calendarRepository = calendarApplication().container.calendarRepository,
                noteRepository = calendarApplication().container.noteRepository
            )
        }

        initializer {
            NoteEditorViewModel(
                this.createSavedStateHandle(),
                calendarApplication().container.noteRepository
            )
        }
    }
}

fun CreationExtras.calendarApplication(): CalendarApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CalendarApplication)
