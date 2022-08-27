package com.example.materialapp.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.example.materialapp.R
import com.example.materialapp.databinding.AddNoteBottomSheetFragmentBinding
import com.example.materialapp.domain.data.notesDB.NoteEntity
import com.example.materialapp.domain.repos.NoteRepository
import com.example.materialapp.ui.viewmodel.AddNoteBottomSheetViewModel
import com.example.materialapp.ui.viewmodel.AddNoteViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

const val UPDATE_NOTES_REQUEST_KEY = "UPDATE_NOTES_REQUEST_KEY"

class AddNoteBottomSheetFragment(private val repository: NoteRepository) :
    BottomSheetDialogFragment() {

    private lateinit var binding: AddNoteBottomSheetFragmentBinding
    private val viewModel: AddNoteBottomSheetViewModel by viewModels {
        AddNoteViewModelFactory(repository)
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
            viewModel.insertNote(getNewNote())
            dismiss()
            setFragmentResult(UPDATE_NOTES_REQUEST_KEY, Bundle())
        }
    }

    private fun getNewNote() =
        NoteEntity(
            UUID.randomUUID().toString(),
            binding.title.text.toString(),
            binding.text.text.toString(),
            Date(System.currentTimeMillis()).toString(),
            if (binding.priority.text.isNullOrEmpty()) MIN_PRIORITY
            else binding.priority.text.toString().toInt()
        )

    companion object {
        const val MIN_PRIORITY = 1
    }
}