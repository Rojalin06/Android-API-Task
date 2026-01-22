package com.example.notesapp

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val dao: NoteDao) {

    // 1. Get All Notes
    val allNotes: Flow<List<NoteEntity>> = dao.getAllNotes()

    // 2. Insert Note
    suspend fun insert(note: NoteEntity) {
        dao.insertNote(note)
    }

    // 3. Delete Note
    suspend fun delete(note: NoteEntity) {
        dao.deleteNote(note)
    }
}