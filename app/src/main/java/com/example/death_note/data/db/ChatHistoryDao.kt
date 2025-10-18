package com.example.death_note.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.death_note.model.ChatHistoryEntity

@Dao
interface ChatHistoryDao {

    @Insert
    suspend fun insert(chatMessage: ChatHistoryEntity)

    @Query("SELECT * FROM chat_history_table ORDER BY timestamp ASC")
    fun getAllChatHistory(): LiveData<List<ChatHistoryEntity>>
}
