package com.example.death_note.ui.chat.note

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.death_note.ui.chat.note.NoteAdapter
import com.example.death_note.ui.chat.note.NoteViewModel
import com.example.death_note.R
import com.example.death_note.model.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesFragment : Fragment() {

    private lateinit var noteViewModel: NoteViewModel
    private val adapter = NoteAdapter()

    private val noteActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                val shouldDelete = it.getBooleanExtra(NoteActivity.EXTRA_DELETE, false)
                val id = it.getIntExtra(NoteActivity.EXTRA_ID, -1)

                if (shouldDelete && id != -1) {
                    // Create a dummy note object with only the ID to delete
                    val noteToDelete =
                        Note(id = id, title = "", content = "", timestamp = 0, color = 0)
                    noteViewModel.deleteNote(noteToDelete)
                    Toast.makeText(requireContext(), "Note deleted", Toast.LENGTH_SHORT).show()
                } else {
                    val title = it.getStringExtra(NoteActivity.EXTRA_TITLE)
                    val content = it.getStringExtra(NoteActivity.EXTRA_CONTENT)
                    if (title != null && content != null) {
                        val timestamp = it.getLongExtra(NoteActivity.EXTRA_TIMESTAMP, 0)
                        val color = it.getIntExtra(NoteActivity.EXTRA_COLOR, 0)
                        val isPinned = it.getBooleanExtra(NoteActivity.EXTRA_PINNED, false)

                        if (id == -1) { // New Note
                            val note = Note(
                                title = title,
                                content = content,
                                timestamp = timestamp,
                                color = color,
                                isPinned = isPinned
                            )
                            noteViewModel.insertNote(note)
                            Toast.makeText(requireContext(), "Note saved", Toast.LENGTH_SHORT).show()
                        } else { // Existing Note
                            val note = Note(
                                id = id,
                                title = title,
                                content = content,
                                timestamp = timestamp,
                                color = color,
                                isPinned = isPinned
                            )
                            noteViewModel.updateNote(note)
                            Toast.makeText(requireContext(), "Note updated", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.notes_recycler_view)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        view.findViewById<EditText>(R.id.search_edit_text).addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val searchQuery = "%${s.toString()}%"
                noteViewModel.searchNotes(searchQuery).observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }
            }
        })

        noteViewModel.allNotes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        view.findViewById<FloatingActionButton>(R.id.fab_add_note).setOnClickListener {
            val intent = Intent(requireContext(), NoteActivity::class.java)
            noteActivityLauncher.launch(intent)
        }

        adapter.onItemClick = {
            val intent = Intent(requireContext(), NoteActivity::class.java).apply {
                putExtra(NoteActivity.EXTRA_ID, it.id)
                putExtra(NoteActivity.EXTRA_TITLE, it.title)
                putExtra(NoteActivity.EXTRA_CONTENT, it.content)
                putExtra(NoteActivity.EXTRA_COLOR, it.color)
                putExtra(NoteActivity.EXTRA_PINNED, it.isPinned)
            }
            noteActivityLauncher.launch(intent)
        }

        return view
    }
}