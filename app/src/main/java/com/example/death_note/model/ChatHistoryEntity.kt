package com.example.death_note.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_history_table")
data class ChatHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val messageType: String, // "USER" or "BOT"
    val timestamp: Long
)