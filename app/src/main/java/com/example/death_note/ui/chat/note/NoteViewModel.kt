package com.example.death_note.ui.chat.note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope

import com.example.death_note.data.db.NoteDatabase

import com.example.death_note.model.Note
import com.example.death_note.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository
    val allNotes: LiveData<List<Note>>

    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun searchNotes(searchQuery: String): LiveData<List<Note>> {
        return repository.searchNotes(searchQuery)
    }
}