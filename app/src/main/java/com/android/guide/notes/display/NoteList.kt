package com.android.guide.notes.display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.guide.notes.R
import com.android.guide.notes.database.AppDataBase
import com.android.guide.notes.database.AppRepository
import com.android.guide.notes.database.Note
import com.android.guide.notes.databinding.FragmentNoteListBinding


class NoteList : Fragment(), INoteAdapter{

    private lateinit var viewModel: NoteListViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: FragmentNoteListBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_note_list,container,false)
        val application = requireNotNull(this.activity).application

        val dataSource = AppRepository(AppDataBase.getDatabase(application).noteDataBaseDao)
        val viewModelFactory = NoteListViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this,viewModelFactory)[NoteListViewModel::class.java]

        binding.noteListViewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

//        val manager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        val manager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        val adapter = NoteAdapter(this)
        binding.notesRecyclerView.layoutManager = manager
        binding.notesRecyclerView.adapter = adapter

        viewModel.allNotes.observe(this.viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.notesRecyclerView.visibility = View.INVISIBLE
                binding.empty.visibility = View.VISIBLE
            } else {
                binding.empty.visibility = View.INVISIBLE
                binding.notesRecyclerView.visibility = View.VISIBLE
            }
            adapter.submitList(it)
        }


        // Set a custom animation for showing and hiding the FAB
        binding.addNoteBtn.apply {
            setOnClickListener {
                moveToAddFrag()
            }
        }


        return binding.root
    }


    private fun moveToAddFrag() {
        findNavController().navigate(NoteListDirections.actionNoteListToAddFragment(-1))
    }


    override fun onItemClicked(note: Note) {
        findNavController().navigate(NoteListDirections.actionNoteListToAddFragment(note.id))
    }

    override fun onItemDelete(note: Note) {
        viewModel.deleteNote(note)
    }

}