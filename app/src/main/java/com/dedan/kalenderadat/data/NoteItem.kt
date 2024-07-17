package com.dedan.kalenderadat.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val content: String,
    val date: String
)
