package com.android.guide.notes.addNote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.guide.notes.database.AppRepository
import com.android.guide.notes.database.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/*
snackBar conditions
0 = Off
1 = Saved Successfully
2 = All Field required
3 = Update Successfully
4 = Deleted Successfully
**/

class AddViewModel(
    private val database:AppRepository,
    private val id:Int
    ) : ViewModel(){

    var note = MutableLiveData<Note>()
    private var _keyboard = MutableLiveData<Boolean>()
    var keyboard :LiveData<Boolean> = _keyboard

    private val _showSnackBar = MutableLiveData<Int>(0)
    val showSnackBar:LiveData<Int> = _showSnackBar

    private val _title = MutableLiveData<String>()
    val title:LiveData<String> = _title

    private var isEditing : Boolean = false

    init {

        isEditing = if(id==-1){
            ui(Note("",""))
            _title.value = "Add Note"
            false
        } else{
            viewModelScope.launch  {
                ui(database.getNote(id))
            }
            _title.value = "Update Note"
            true
        }
    }

    private fun ui(note: Note) {
        this.note.value = note
    }

    fun save(){

        if (note.value!!.isEmpty){
            _showSnackBar.value = 2
            return
        } else{
            if (!isEditing){
                insert(note.value!!)
                _showSnackBar.value = 1
            } else {
                updateNote(note.value!!)
                _showSnackBar.value = 3
            }
            back()
        }

    }

    fun back(){
        if (isEditing && note.value!!.isEmpty){
            deleteNote(note.value!!)
            _showSnackBar.value = 4
        }
        _keyboard.value = true
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

}


