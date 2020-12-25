package com.android.guide.notes.addNote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.guide.notes.R
import com.android.guide.notes.database.NoteDataBase
import com.android.guide.notes.database.NoteRepository
import com.android.guide.notes.databinding.FragmentAddBinding
import com.google.android.material.snackbar.Snackbar

class AddFragment : Fragment() {

    lateinit var binding: FragmentAddBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)


        val noteId = AddFragmentArgs.fromBundle(requireArguments()).id

        // context
        val application = requireNotNull(this.activity).application
        // data source using Dao
        val dataSource = NoteRepository(NoteDataBase.getDatabase(application).noteDataBaseDao)
        // view model factory to use data source
        val viewModelFactory = AddViewModelFactory(dataSource,requireActivity(),noteId)
        // view Model
        val viewModel = ViewModelProvider(this, viewModelFactory)[AddViewModel::class.java]


        binding.addViewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        viewModel.showSnackBar.observe(viewLifecycleOwner,{
            if (it){
                showSnackBar()
            }
        })

        return binding.root
    }

    private fun showSnackBar() {
        Snackbar.make(binding.mainContentAddNote,"All Field required",Snackbar.LENGTH_SHORT).show()
    }

}