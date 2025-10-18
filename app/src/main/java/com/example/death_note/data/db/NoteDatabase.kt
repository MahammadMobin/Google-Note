package com.example.death_note.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.death_note.model.ChatHistoryEntity
import com.example.death_note.model.Note

@Database(entities = [Note::class, ChatHistoryEntity::class], version = 4, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao
    abstract fun getChatHistoryDao(): ChatHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}