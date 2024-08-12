package com.example.noteappcompose.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteappcompose.data.entities.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase():RoomDatabase() {
    abstract fun getNoteDao():NotesDao
}