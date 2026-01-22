package com.example.notesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NoteRepository) : ViewModel() {

    // 1. Live Data Stream
    val notes = repository.allNotes

    // 2. Add Note Function
    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insert(NoteEntity(title = title, content = content))
        }
    }

    // 3. Delete Note Function
    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }
}