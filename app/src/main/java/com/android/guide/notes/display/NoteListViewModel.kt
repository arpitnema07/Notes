package com.android.guide.notes.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.guide.notes.database.Note
import com.android.guide.notes.database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteListViewModel(private val repository: NoteRepository) : ViewModel() {


    val allNotes = repository.allNotes


    fun deleteNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun deleteAllNote() = viewModelScope.launch(Dispatchers.IO){
        repository.deleteAll()
    }

}