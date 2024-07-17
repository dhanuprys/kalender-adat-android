package com.dedan.kalenderadat.data

import android.util.Log
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNoteByDate(date: String): Flow<NoteItem?>
    suspend fun writeNote(note: NoteItem)
    suspend fun deleteNote(note: NoteItem)
}

class LocalNoteRepository(
    private val noteDao: NoteDao
) : NoteRepository {
    override fun getNoteByDate(date: String): Flow<NoteItem?> {
        Log.d("Database", date)
        return noteDao.getNoteByDateId(date)
    }

    override suspend fun writeNote(note: NoteItem) {
        noteDao.writeNote(note)
    }

    override suspend fun deleteNote(note: NoteItem) {
        noteDao.deleteNote(note)
    }
}