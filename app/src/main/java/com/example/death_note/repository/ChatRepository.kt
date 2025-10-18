package com.example.death_note.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.death_note.data.db.ChatHistoryDao
import com.example.death_note.data.db.NoteDatabase
import com.example.death_note.gemini.ContentRequest
import com.example.death_note.gemini.GeminiRequest
import com.example.death_note.gemini.PartRequest
import com.example.death_note.gemini.RetrofitClient
import com.example.death_note.model.ChatHistoryEntity
import com.example.death_note.model.ChatMessage
import com.example.death_note.model.MessageType

class ChatRepository(application: Application) {

    private val apiService = RetrofitClient.instance
    private val chatHistoryDao: ChatHistoryDao


    init {
        val database = NoteDatabase.Companion.getDatabase(application)
        chatHistoryDao = database.getChatHistoryDao()
    }

    fun getAllChatHistory(): LiveData<List<ChatHistoryEntity>> {
        return chatHistoryDao.getAllChatHistory()
    }

    suspend fun sendMessageToGemini(message: String): ChatMessage {
        // Save user message to database
        val userMessageEntity = ChatHistoryEntity(
            text = message,
            messageType = "USER",
            timestamp = System.currentTimeMillis()
        )
        chatHistoryDao.insert(userMessageEntity)

        val request = GeminiRequest(
            contents = listOf(
                ContentRequest(
                    parts = listOf(
                        PartRequest(text = message)
                    )
                )
            )
        )

        return try {
            val response = apiService.generateContent("gemini-pro", request)
            if (response.isSuccessful) {
                val botResponseText = response.body()?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "Sorry, I couldn't understand."

                // Save bot message to database
                val botMessageEntity = ChatHistoryEntity(
                    text = botResponseText,
                    messageType = "BOT",
                    timestamp = System.currentTimeMillis()
                )
                chatHistoryDao.insert(botMessageEntity)

                ChatMessage(botResponseText, MessageType.BOT)
            } else {
                ChatMessage("Error: ${response.code()} - ${response.message()}", MessageType.BOT)
            }
        } catch (e: Exception) {
            ChatMessage("Error: ${e.message}", MessageType.BOT)
        }
    }
}