package com.example.notepad.repository

import androidx.lifecycle.LiveData
import com.example.notepad.data.NoteDao
import com.example.notepad.model.Note

class Repository(private val noteDao : NoteDao) {

    val readAllNotes : LiveData<List<Note>> = noteDao.readAllNotes()

    suspend fun addNotes(note : Note){
        noteDao.addNote(note)
    }
    suspend fun updateNote(note : Note){
        noteDao.updateNote(note)
    }
    suspend fun deleteNote(note : Note){
        noteDao.delete(note)
    }

    fun deleteAllNotes(){
        noteDao.deleteAllNotes()
    }
}