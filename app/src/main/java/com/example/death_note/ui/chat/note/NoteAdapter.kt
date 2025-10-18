package com.example.death_note.ui.chat.note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.death_note.R
import com.example.death_note.model.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteAdapter : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback()) {

    var onItemClick: ((Note) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = getItem(position)
        holder.bind(currentNote)
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        private val textViewContent: TextView = itemView.findViewById(R.id.text_view_content)
        private val textViewTimestamp: TextView = itemView.findViewById(R.id.text_view_timestamp)
        private val cardView: CardView = itemView.findViewById(R.id.card_view)
        private val pinIcon: ImageView = itemView.findViewById(R.id.pin_icon)
        private val colorIndicator: View = itemView.findViewById(R.id.color_indicator)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(getItem(position))
                }
            }
        }

        fun bind(note: Note) {
            textViewTitle.text = note.title
            textViewContent.text = note.content
            textViewTimestamp.text = formatDate(note.timestamp)
            cardView.setCardBackgroundColor(note.color)
            colorIndicator.setBackgroundColor(note.color)

            if (note.isPinned) {
                pinIcon.visibility = View.VISIBLE
            } else {
                pinIcon.visibility = View.GONE
            }
        }

        private fun formatDate(timestamp: Long): String {
            val sdf = SimpleDateFormat("dd/MM/yy hh:mm a", Locale.getDefault())
            return sdf.format(Date(timestamp))
        }
    }

    class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}