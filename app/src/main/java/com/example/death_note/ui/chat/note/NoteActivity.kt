package com.example.death_note.ui.chat.note

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.death_note.R

class NoteActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextContent: EditText
    private lateinit var pinButton: ImageView
    private lateinit var saveButton: ImageView
    private lateinit var deleteButton: ImageView
    private lateinit var colorPickerContainer: LinearLayout

    private var noteId: Int = -1
    private var noteColor: Int = Color.WHITE // Default color
    private var isPinned: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        val toolbar: Toolbar = findViewById(R.id.toolbar_note)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // Hide default title

        editTextTitle = findViewById(R.id.edit_text_title)
        editTextContent = findViewById(R.id.edit_text_content)
        pinButton = findViewById(R.id.pin_button)
        saveButton = findViewById(R.id.button_save)
        deleteButton = findViewById(R.id.button_delete)
        colorPickerContainer = findViewById(R.id.color_picker_container)

        if (intent.hasExtra(EXTRA_ID)) {
            noteId = intent.getIntExtra(EXTRA_ID, -1)
            noteColor = intent.getIntExtra(EXTRA_COLOR, ContextCompat.getColor(this, R.color.note_color_default))
            isPinned = intent.getBooleanExtra(EXTRA_PINNED, false)
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE))
            editTextContent.setText(intent.getStringExtra(EXTRA_CONTENT))
            updatePinIcon()
            deleteButton.visibility = View.VISIBLE // Show delete button for existing notes
        } else {
            noteColor = ContextCompat.getColor(this, R.color.note_color_default) // Set default color for new note
            deleteButton.visibility = View.GONE // Hide delete button for new notes
        }

        updateActivityBackgroundColor(noteColor)
        setupColorPicker()

        pinButton.setOnClickListener {
            isPinned = !isPinned
            updatePinIcon()
        }

        saveButton.setOnClickListener {
            saveNote()
        }

        deleteButton.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun updatePinIcon() {
        if (isPinned) {
            pinButton.setImageResource(R.drawable.ic_pinned)
        } else {
            pinButton.setImageResource(R.drawable.ic_unpinned)
        }
    }

    private fun setupColorPicker() {
        val colors = listOf(
            R.color.note_color_default,
            R.color.note_color_red,
            R.color.note_color_blue,
            R.color.note_color_green,
            R.color.note_color_yellow,
            R.color.note_color_grey
        )

        // Dynamically add color views to the container
        colorPickerContainer.removeAllViews()
        for (colorResId in colors) {
            val colorView = layoutInflater.inflate(R.layout.color_picker_item, colorPickerContainer, false)
            val colorCircle: ImageView = colorView.findViewById(R.id.color_circle)
            colorCircle.setColorFilter(ContextCompat.getColor(this, colorResId))

            colorView.setOnClickListener {
                noteColor = ContextCompat.getColor(this, colorResId)
                updateActivityBackgroundColor(noteColor)
            }
            colorPickerContainer.addView(colorView)
        }
    }

    private fun updateActivityBackgroundColor(color: Int) {
        findViewById<View>(android.R.id.content).setBackgroundColor(color)
    }

    private fun saveNote() {
        val title = editTextTitle.text.toString()
        val content = editTextContent.text.toString()
        val timestamp = System.currentTimeMillis()

        if (title.isBlank() && content.isBlank()) { return }

        val data = Intent().apply {
            putExtra(EXTRA_ID, noteId)
            putExtra(EXTRA_TITLE, title)
            putExtra(EXTRA_CONTENT, content)
            putExtra(EXTRA_TIMESTAMP, timestamp)
            putExtra(EXTRA_COLOR, noteColor)
            putExtra(EXTRA_PINNED, isPinned)
        }
        setResult(RESULT_OK, data)
        finish()
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Yes") { _, _ -> deleteNote() }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteNote() {
        val data = Intent().apply {
            putExtra(EXTRA_ID, noteId)
            putExtra(EXTRA_DELETE, true)
        }
        setResult(RESULT_OK, data)
        finish()
    }

    companion object {
        const val EXTRA_ID = "com.example.death_note.EXTRA_ID"
        const val EXTRA_TITLE = "com.example.death_note.EXTRA_TITLE"
        const val EXTRA_CONTENT = "com.example.death_note.EXTRA_CONTENT"
        const val EXTRA_TIMESTAMP = "com.example.death_note.EXTRA_TIMESTAMP"
        const val EXTRA_COLOR = "com.example.death_note.EXTRA_COLOR"
        const val EXTRA_PINNED = "com.example.death_note.EXTRA_PINNED"
        const val EXTRA_DELETE = "com.example.death_note.EXTRA_DELETE"
    }
}