package com.android.guide.notes.addNote

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.guide.notes.database.NoteRepository

class AddViewModelFactory(private val dataSource:NoteRepository,private val activity: Activity,private val id: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)){
            return AddViewModel(dataSource,activity,id) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }


}