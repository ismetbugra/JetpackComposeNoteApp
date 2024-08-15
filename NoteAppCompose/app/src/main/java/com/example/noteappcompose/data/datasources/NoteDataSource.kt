package com.example.noteappcompose.data.datasources

import com.example.noteappcompose.data.entities.Note
import com.example.noteappcompose.data.room.NotesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteDataSource @Inject constructor(var ndao:NotesDao) {

    suspend fun getAllNotes():List<Note> =
        withContext(Dispatchers.IO){
            return@withContext ndao.getAllNotes()
        }

    // ekleme işlemi main threadde olmuyordu uı bloclaması ytuzunden
    suspend fun insertNote(note: Note){
        withContext(Dispatchers.IO) {
            ndao.insertNote(note)
        }

    }

    suspend fun updateNote(note: Note){
        withContext(Dispatchers.IO){
            ndao.updateNote(note)
        }

    }

    suspend fun deleteNote(note: Note){
        withContext(Dispatchers.IO){
            ndao.deleteNote(note)
        }
    }

    suspend fun getFavorites():List<Note> =
        withContext(Dispatchers.IO){
            return@withContext ndao.getFavorites()
        }

}