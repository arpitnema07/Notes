package com.android.guide.notes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes_table")
data class Note(
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

}