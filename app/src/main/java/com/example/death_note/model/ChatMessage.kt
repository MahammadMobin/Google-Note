package com.example.death_note.model

enum class MessageType {
    USER,
    BOT,
    LOADING
}

data class ChatMessage(
    val text: String,
    val type: MessageType
)
