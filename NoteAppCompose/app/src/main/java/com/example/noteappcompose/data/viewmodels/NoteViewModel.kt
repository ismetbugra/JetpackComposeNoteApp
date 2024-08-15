package com.example.noteappcompose.data.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappcompose.data.entities.Note
import com.example.noteappcompose.data.repositories.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(var nrepo:NoteRepository) :ViewModel() {

    var noteList = mutableStateOf<List<Note>>(listOf())
    private val _list = MutableStateFlow<List<Note>>(listOf())
    var list = _list.asStateFlow()

    var favoritesList= mutableStateOf<List<Note>>(listOf())


    init {
        getAllNotes()
        getFavorites()
    }

    fun getAllNotes(){
        CoroutineScope(Dispatchers.Main).launch {
            //noteList.value=nrepo.getAllNotes()
            _list.value=nrepo.getAllNotes()

        }
    }

    fun insertNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            nrepo.insertNote(note)
            getAllNotes()
        }
    }

    fun updateNote(note: Note){
        CoroutineScope(Dispatchers.Main).launch {
            nrepo.updateNote(note)
        }
    }

    fun deleteNote(note: Note){
        CoroutineScope(Dispatchers.Main).launch {
            nrepo.deleteNote(note)
        }
    }

    fun getFavorites(){
        CoroutineScope(Dispatchers.IO).launch {
            favoritesList.value=nrepo.getFavorites()
        }
    }
}