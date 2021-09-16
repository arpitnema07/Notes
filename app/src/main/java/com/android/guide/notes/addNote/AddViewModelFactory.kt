package com.android.guide.notes.addNote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.guide.notes.database.AppRepository

class AddViewModelFactory(private val dataSource:AppRepository, private val id: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)){
            return AddViewModel(dataSource,id) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }


}