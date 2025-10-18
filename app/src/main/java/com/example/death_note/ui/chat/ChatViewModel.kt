package com.example.death_note.ui.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.death_note.model.ChatHistoryEntity
import com.example.death_note.repository.ChatRepository
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ChatRepository
    val chatHistory: LiveData<List<ChatHistoryEntity>>

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        repository = ChatRepository(application)
        chatHistory = repository.getAllChatHistory()
    }

    fun sendMessage(message: String) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.sendMessageToGemini(message)
            _isLoading.value = false
        }
    }
}