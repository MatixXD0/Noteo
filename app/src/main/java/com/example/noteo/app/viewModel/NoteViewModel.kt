package com.example.noteo.app.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteo.app.model.Graph
import com.example.noteo.app.model.note.Note
import com.example.noteo.app.model.note.NotesRepository
import com.example.noteo.ui.theme.noteColorDefault
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val notesRepository: NotesRepository = Graph.notesRepository
) : ViewModel() {
    var noteNameState by mutableStateOf("")
    var noteDescriptionState by mutableStateOf("")
    var noteColorState by mutableIntStateOf(noteColorDefault.toArgb())

    lateinit var getAllNotes: Flow<List<Note>>

    init {
        viewModelScope.launch {
            getAllNotes = notesRepository.getNote()
        }
    }

    fun onNoteNameChanged(
        newString: String
    ) {
        noteNameState = newString
    }

    fun onNoteDescriptionChanged(
        newString: String
    ) {
        noteDescriptionState = newString
    }

    fun onNoteColorChanged(newColor: Int) {
        noteColorState = newColor
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.addNote(note = note)
        }
    }

    fun getNoteById(id: Long): Flow<Note> {
        return notesRepository.getNoteById(id = id)
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.updateNote(note = note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.deleteNote(note = note)
        }
    }
}