package com.android.guide.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 3, exportSchema = false)
abstract class NoteDataBase : RoomDatabase(){

    abstract val noteDataBaseDao : NoteDao

    companion object{
        @Volatile
        private var INSTANCE : NoteDataBase?=null

        fun getDatabase(context: Context): NoteDataBase {

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext, NoteDataBase::class.java,"notes_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance

                instance
            }
        }
    }

}