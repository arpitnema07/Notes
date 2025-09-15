package com.android.guide.notes.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.guide.notes.database.AppRepository

class NoteListViewModelFactory(private val dataSource: AppRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteListViewModel::class.java)) {
            return NoteListViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}