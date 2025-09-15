package com.android.guide.notes

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.android.guide.notes.database.Note


/**
 * Created by Arpit Nema on 12/31/2020
 */

@BindingAdapter("noteTitleString")
fun TextView.setNoteTitleString(item: Note){
    var title = item.title
    if (title.length>10){
        title = title.substring(0..8) + context.getString(R.string.dot)
    }
    text = title
}

@BindingAdapter("noteTextString")
fun TextView.setNoteTextString(item: Note){
    var t = item.text
    if (t.length>50){
        t = t.substring(0..50) + context.getString(R.string.dot)
    }
    text = t
}

@BindingAdapter("noteDateString")
fun TextView.setNoteDateString(item: Note){
    text = item.date.substring(0..10)
}