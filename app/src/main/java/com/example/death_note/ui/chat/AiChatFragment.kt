package com.example.death_note.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.death_note.ui.chat.ChatAdapter
import com.example.death_note.R
import com.example.death_note.ui.chat.ChatViewModel
import com.example.death_note.model.ChatMessage
import com.example.death_note.model.MessageType
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AiChatFragment : Fragment() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ai_chat, container, false)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(ChatViewModel::class.java)

        val recyclerView: RecyclerView = view.findViewById(R.id.chat_recycler_view)
        val messageInput: EditText = view.findViewById(R.id.message_input)
        val sendButton: FloatingActionButton = view.findViewById(R.id.send_button)

        val initialMessages = mutableListOf<ChatMessage>()
        chatAdapter = ChatAdapter(initialMessages)
        recyclerView.adapter = chatAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext()).apply {
            stackFromEnd = true
        }

        viewModel.chatHistory.observe(viewLifecycleOwner) {
            val chatMessages = it.map { entity ->
                val messageType = if (entity.messageType == "USER") MessageType.USER else MessageType.BOT
                ChatMessage(entity.text, messageType)
            }.toMutableList()

            chatAdapter = ChatAdapter(chatMessages)
            recyclerView.adapter = chatAdapter
            recyclerView.scrollToPosition(chatMessages.size - 1)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                // You can show a loading indicator here if you want
            }
        }

        sendButton.setOnClickListener {
            val message = messageInput.text.toString()
            if (message.isNotBlank()) {
                viewModel.sendMessage(message)
                messageInput.text.clear()
            }
        }

        return view
    }
}