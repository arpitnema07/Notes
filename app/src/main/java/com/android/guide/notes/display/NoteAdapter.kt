package com.android.guide.notes.display

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.guide.notes.database.Note
import com.android.guide.notes.databinding.ItemNoteBinding

class NoteAdapter(private val listener: INoteAdapter) : ListAdapter<Note,NoteAdapter.ViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position),listener)
    }


    class ViewHolder private constructor(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note, listener: INoteAdapter) {
            binding.note = item
            binding.executePendingBindings()
            binding.clickListener = listener
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNoteBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }


}

interface INoteAdapter{
    fun onItemClicked(note: Note)
    fun onItemDelete(note: Note)
}

class NoteDiffCallback:DiffUtil.ItemCallback<Note>(){
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}