package com.android.guide.notes.addNote

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.guide.notes.database.Note
import com.android.guide.notes.database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddViewModel(
    private val database:NoteRepository,
    private val activity: Activity,
    private val id:Int) : ViewModel(){

    var note = MutableLiveData<Note>()

    private val _showSnackBar = MutableLiveData<Boolean>()
    val showSnackBar:LiveData<Boolean> = _showSnackBar

    private var isEditing : Boolean = false

    init {
        isEditing = if(id==-1){
            ui(Note("",""))
            false
        } else{
            viewModelScope.launch  {
                ui(database.getNote(id))
            }
            true
        }
    }

    private fun ui(note: Note) {
        this.note.value = note
    }

    fun save(){

        if (note.value!!.isEmpty){
            _showSnackBar.value =true
            return
        } else{
            if (!isEditing){
                insert(note.value!!)
            } else {
                updateNote(note.value!!)
            }
            back()
        }

    }

    fun back(){
        if (isEditing && note.value!!.isEmpty){
            deleteNote(note.value!!)
        }
        hideKeyboard()
        activity.onBackPressed()
    }

    private fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO){
        database.insert(note)
    }

    private fun updateNote(note: Note) = viewModelScope.launch (Dispatchers.IO){
        database.update(note)
    }

    private fun deleteNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        database.delete(note)
    }

    private fun hideKeyboard(): Boolean {
        return (activity.applicationContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow((activity.currentFocus ?: View(activity.applicationContext)).windowToken, 0)
    }
}


