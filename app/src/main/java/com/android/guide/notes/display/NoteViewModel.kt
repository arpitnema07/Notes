package com.android.guide.notes.display

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.android.guide.notes.database.Note
import com.android.guide.notes.database.NoteDataBase
import com.android.guide.notes.database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val allNotes:LiveData<List<Note>>
    private val repository : NoteRepository
    init {
        val dao = NoteDataBase.getDatabase(application).noteDataBaseDao
         repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    fun deleteNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
}