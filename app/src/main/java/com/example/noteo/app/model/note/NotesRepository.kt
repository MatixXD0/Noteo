package com.example.noteo.app.model.note

import kotlinx.coroutines.flow.Flow

class NotesRepository(
    private val notesDao: NotesDao
) {

    suspend fun addNote(note: Note) {
        notesDao.addNote(note)
    }

    fun getNote(): Flow<List<Note>> = notesDao.getAllNotes()

    fun getNoteById(id: Long): Flow<Note> {
        return notesDao.getNoteById(id)
    }

    suspend fun updateNote(note: Note) {
        notesDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        notesDao.deleteNote(note)
    }
}

