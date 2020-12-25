package com.android.guide.notes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes_table")
class Note(
        @ColumnInfo(name = "title")
        var title:String,
        @ColumnInfo(name = "description")
        var text:String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    @ColumnInfo(name = "date")
    var date = Calendar.getInstance().time.toString()

    val isEmpty
        get() = title.isEmpty() || text.isEmpty()

    fun compare(note: Note):Boolean{
        return title == note.title && text == note.text && id == note.id && date == note.date
    }

    fun compareWithNoDate(note: Note):Boolean{
        return title == note.title && text == note.text && id == note.id
    }
}