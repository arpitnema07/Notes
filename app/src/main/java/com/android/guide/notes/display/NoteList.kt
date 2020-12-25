package com.android.guide.notes.display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.guide.notes.R
import com.android.guide.notes.database.Note
import com.android.guide.notes.database.NoteDataBase
import com.android.guide.notes.database.NoteRepository
import com.android.guide.notes.databinding.FragmentNoteListBinding

class NoteList : Fragment() , INoteAdapter {

    private lateinit var viewModel: NoteListViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: FragmentNoteListBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_note_list,container,false)
        val application = requireNotNull(this.activity).application

        val dataSource = NoteRepository(NoteDataBase.getDatabase(application).noteDataBaseDao)
        val viewModelFactory = NoteListViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this,viewModelFactory)[NoteListViewModel::class.java]

        binding.noteListViewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = NoteAdapter(requireContext(),this)
        binding.notesRecyclerView.adapter = adapter



        viewModel.allNotes.observe(viewLifecycleOwner,{
            it?.let {
                adapter.updateList(it)
            }
        })
        binding.addNoteBtn.setOnClickListener {
            findNavController().navigate(NoteListDirections.actionNoteListToAddFragment(-1))
        }


        return binding.root
    }


    override fun onItemClicked(note: Note) {
        findNavController().navigate(NoteListDirections.actionNoteListToAddFragment(note.id))
    }

    override fun onItemDelete(note: Note) {
        viewModel.deleteNote(note)
    }

}