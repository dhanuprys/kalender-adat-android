package com.dedan.kalenderadat.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY date DESC")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query("SELECT * FROM notes WHERE date = :date  LIMIT 1")
    fun getNoteByDateId(date: String): Flow<NoteItem?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun writeNote(note: NoteItem)

    @Delete
    suspend fun deleteNote(note: NoteItem)
}