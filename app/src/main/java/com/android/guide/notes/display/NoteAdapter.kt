package com.android.guide.notes.display

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.guide.notes.R
import com.android.guide.notes.database.Note

class NoteAdapter(private val context: Context, private val listener: INoteAdapter) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

        val notes = ArrayList<Note>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_note, parent, false)
        )
        viewHolder.deleteButton.setOnClickListener {
            listener.onItemDelete(notes[viewHolder.adapterPosition])
        }
        viewHolder.main.setOnClickListener {
            listener.onItemClicked(notes[viewHolder.adapterPosition])
        }


        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = notes[position].title
    }

    override fun getItemCount(): Int {
        return notes.size
    }


    fun updateList(newList: List<Note>){
        notes.clear()
        notes.addAll(newList)

        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val main: ConstraintLayout = itemView.findViewById(R.id.note_item)
        val textView: TextView = itemView.findViewById(R.id.text)
        val deleteButton: ImageView = itemView.findViewById(R.id.delete)

    }
}
interface INoteAdapter{
    fun onItemClicked(note: Note)
    fun onItemDelete(note: Note)
}