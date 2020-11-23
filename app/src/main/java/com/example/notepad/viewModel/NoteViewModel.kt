package com.example.notepad.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notepad.data.NoteDatabase
import com.example.notepad.model.Note
import com.example.notepad.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val readAllNotes: LiveData<List<Note>>
    private val repository: Repository

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = Repository(noteDao)
        readAllNotes = repository.readAllNotes
    }

    fun addNote(note:Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNotes(note)
        }
    }
    fun updateNote(note:Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }
    fun deleteAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllNotes()
        }
    }
    fun deleteNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

}