package com.example.death_note.repository

import androidx.lifecycle.LiveData
import com.example.death_note.data.db.NoteDao

import com.example.death_note.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    fun searchNotes(searchQuery: String): LiveData<List<Note>> {
        return noteDao.searchNotes(searchQuery)
    }
}