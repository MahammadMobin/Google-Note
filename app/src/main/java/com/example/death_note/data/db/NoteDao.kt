package com.example.death_note.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
// Corrected import
import com.example.death_note.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM note_table ORDER BY isPinned DESC, timestamp DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE title LIKE :searchQuery OR content LIKE :searchQuery ORDER BY isPinned DESC, timestamp DESC")
    fun searchNotes(searchQuery: String): LiveData<List<Note>>
}
