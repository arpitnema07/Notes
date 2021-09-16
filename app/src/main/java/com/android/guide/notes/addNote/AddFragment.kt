package com.android.guide.notes.addNote

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.guide.notes.database.AppDataBase
import com.android.guide.notes.database.AppRepository
import com.android.guide.notes.databinding.FragmentAddBinding
import com.google.android.material.snackbar.Snackbar

class AddFragment : Fragment() {

    lateinit var binding: FragmentAddBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddBinding.inflate(inflater,container, false)


        val noteId = AddFragmentArgs.fromBundle(requireArguments()).id

        // context
        val application = requireNotNull(this.activity).application
        // data source using Dao
        val dataSource = AppRepository(AppDataBase.getDatabase(application).noteDataBaseDao)
        // view model factory to use data source
        val viewModelFactory = AddViewModelFactory(dataSource,noteId)
        // view Model
        val viewModel = ViewModelProvider(this, viewModelFactory)[AddViewModel::class.java]


        binding.addViewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        viewModel.showSnackBar.observe(viewLifecycleOwner,{
            when (it) {
                2 -> {
                    showSnackBar("All Field required")
                }
                1 -> {
                    showSnackBar("Saved Successfully")
                }
                3 -> {
                    showSnackBar("Update Successfully")
                }
                4 -> {
                    showSnackBar("Deleted Successfully")
                }
            }
        })
        viewModel.keyboard.observe(viewLifecycleOwner,{
            if (it) {
                (requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(
                        (activity?.currentFocus ?: View(requireContext())).windowToken, 0
                    )

                activity?.onBackPressed()
            }
        })
        return binding.root
    }

    private fun showSnackBar(s: String) {
        Snackbar.make(binding.mainContentAddNote,s,Snackbar.LENGTH_SHORT).show()
    }

}