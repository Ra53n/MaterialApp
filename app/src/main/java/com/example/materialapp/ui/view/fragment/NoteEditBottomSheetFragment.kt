package com.example.materialapp.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.example.materialapp.R
import com.example.materialapp.databinding.NoteEditBottomSheetFragmentBinding
import com.example.materialapp.domain.data.notesDB.NoteEntity
import com.example.materialapp.domain.repos.NoteRepository
import com.example.materialapp.ui.viewmodel.NoteEditBottomSheetViewModel
import com.example.materialapp.ui.viewmodel.NoteEditViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class NoteEditBottomSheetFragment(val repository: NoteRepository, val note: NoteEntity) :
    BottomSheetDialogFragment() {

    private lateinit var binding: NoteEditBottomSheetFragmentBinding
    private val viewModel: NoteEditBottomSheetViewModel by viewModels {
        NoteEditViewModelFactory(repository)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NoteEditBottomSheetFragmentBinding.bind(
            inflater.inflate(R.layout.note_edit_bottom_sheet_fragment, container)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.setText(note.title)
        binding.text.setText(note.note)
        binding.date.text = note.date
        binding.priorityTV.setText(note.priority.toString())
        bindViews()
    }

    private fun bindViews() {
        binding.addButton.setOnClickListener {
            viewModel.updateNote(getEditedNote())
            dismiss()
            setFragmentResult(UPDATE_NOTES_REQUEST_KEY, Bundle())
        }
    }

    private fun getEditedNote() =
        NoteEntity(
            note.id,
            binding.title.text.toString(),
            binding.text.text.toString(),
            Date(System.currentTimeMillis()).toString(),
            if (binding.priorityTV.text.isNullOrEmpty()) MIN_PRIORITY
            else binding.priorityTV.text.toString().toInt()
        )

    companion object {
        const val MIN_PRIORITY = 1
    }

}