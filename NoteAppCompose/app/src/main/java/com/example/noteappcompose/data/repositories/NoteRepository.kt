package com.example.noteappcompose.data.repositories

import com.example.noteappcompose.data.datasources.NoteDataSource
import com.example.noteappcompose.data.entities.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(var ndso:NoteDataSource) {

    suspend fun getAllNotes():List<Note> = ndso.getAllNotes()
    suspend fun insertNote(note: Note) = ndso.insertNote(note)
    suspend fun updateNote(note: Note) = ndso.updateNote(note)
    suspend fun deleteNote(note: Note) = ndso.deleteNote(note)
}