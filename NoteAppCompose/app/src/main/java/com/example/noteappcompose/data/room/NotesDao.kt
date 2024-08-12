package com.example.noteappcompose.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.noteappcompose.data.entities.Note

@Dao
interface NotesDao {

    @Query("SELECT*FROM notes")
    fun getAllNotes():List<Note>

    @Insert
    fun insertNote(note:Note)

    @Delete
    fun deleteNote(note: Note)

    @Delete
    fun deleteAllNotes(noteList:List<Note>)

    @Update
    fun updateNote(note: Note)

    @Query("SELECT*FROM notes WHERE note_favorites = 1")
    fun getFavorites():List<Note>
}