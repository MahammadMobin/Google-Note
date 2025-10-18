package com.example.death_note.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val isPinned: Boolean = false
)