package com.example.materialapp.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.example.materialapp.R
import com.example.materialapp.databinding.AddNoteBottomSheetFragmentBinding
import com.example.materialapp.domain.interactors.NotesInteractor
import com.example.materialapp.ui.viewmodel.AddNoteBottomSheetViewModel
import com.example.materialapp.ui.viewmodel.AddNoteViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

const val UPDATE_NOTES_REQUEST_KEY = "UPDATE_NOTES_REQUEST_KEY"

class AddNoteBottomSheetFragment(private val interactor: NotesInteractor) :
    BottomSheetDialogFragment() {

    private lateinit var binding: AddNoteBottomSheetFragmentBinding
    private val viewModel: AddNoteBottomSheetViewModel by viewModels {
        AddNoteViewModelFactory(interactor)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddNoteBottomSheetFragmentBinding.bind(
            inflater.inflate(
                R.layout.add_note_bottom_sheet_fragment,
                container
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    private fun bindViews() {
        binding.addButton.setOnClickListener {
            viewModel.insertNote(
                binding.title.text.toString(),
                binding.text.text.toString(),
                binding.priority.text.toString()
            )
            dismiss()
            setFragmentResult(UPDATE_NOTES_REQUEST_KEY, Bundle())
        }
    }
}