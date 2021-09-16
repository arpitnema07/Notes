package com.android.guide.notes.database

import androidx.lifecycle.LiveData

class AppRepository(private val notesDao: NoteDao) {
    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    suspend fun insert(note: Note){
        notesDao.insert(note)
    }
    suspend fun delete(note: Note){
        notesDao.delete(note)
    }

    suspend fun getNote(key:Int) : Note{
        return notesDao.getNote(key)
    }
    suspend fun update(note: Note){
        notesDao.update(note)
    }
    suspend fun deleteAll(){
        notesDao.deleteAllNotes()
    }
}