package com.example.materialapp.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.materialapp.R
import com.example.materialapp.databinding.NotesFragmentBinding
import com.example.materialapp.domain.repos.NoteRepositoryImpl
import com.example.materialapp.ui.view.adapter.NotesFragmentAdapter
import com.example.materialapp.ui.view.utils.ItemTouchHelperCallback
import com.example.materialapp.ui.viewmodel.NotesViewModel
import com.example.materialapp.ui.viewmodel.NotesViewModelFactory

class NotesFragment : Fragment(R.layout.notes_fragment) {

    private lateinit var binding: NotesFragmentBinding
    private val adapter = NotesFragmentAdapter { viewModel.removeItem(it) }
    private val viewModel: NotesViewModel by viewModels {
        NotesViewModelFactory(NoteRepositoryImpl(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = NotesFragmentBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        ItemTouchHelper(ItemTouchHelperCallback { adapter.removeItem(it) }).attachToRecyclerView(
            binding.recyclerView
        )

        binding.addButton.setOnClickListener { viewModel.onAddClick(parentFragmentManager) }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.notes.collect { list ->
                list?.let { adapter.submitList(it) }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestNotes()
    }
}