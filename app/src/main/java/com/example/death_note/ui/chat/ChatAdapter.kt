package com.example.death_note.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.death_note.R
import com.example.death_note.model.ChatMessage
import com.example.death_note.model.MessageType

class ChatAdapter(private val messages: MutableList<ChatMessage>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_BOT = 2
        private const val VIEW_TYPE_LOADING = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (messages[position].type) {
            MessageType.USER -> VIEW_TYPE_USER
            MessageType.BOT -> VIEW_TYPE_BOT
            MessageType.LOADING -> VIEW_TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_USER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_user, parent, false)
                UserMessageViewHolder(view)
            }
            VIEW_TYPE_BOT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_bot, parent, false)
                BotMessageViewHolder(view)
            }
            VIEW_TYPE_LOADING -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_loading, parent, false)
                LoadingViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserMessageViewHolder -> holder.bind(messages[position])
            is BotMessageViewHolder -> holder.bind(messages[position])
            // No binding needed for LoadingViewHolder
        }
    }

    override fun getItemCount(): Int = messages.size

    // --- ViewHolder Classes ---

    class UserMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.message_text)
        fun bind(message: ChatMessage) {
            messageText.text = message.text
        }
    }

    class BotMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageText: TextView = itemView.findViewById(R.id.message_text)
        fun bind(message: ChatMessage) {
            messageText.text = message.text
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}