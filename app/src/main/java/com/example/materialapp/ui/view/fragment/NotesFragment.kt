package com.example.materialapp.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.materialapp.R
import com.example.materialapp.databinding.NotesFragmentBinding
import com.example.materialapp.domain.interactors.NotesInteractor
import com.example.materialapp.domain.repos.NoteRepositoryImpl
import com.example.materialapp.ui.view.adapter.NotesFragmentAdapter
import com.example.materialapp.ui.view.utils.ItemTouchHelperCallback
import com.example.materialapp.ui.viewmodel.NotesViewModel
import com.example.materialapp.ui.viewmodel.NotesViewModelFactory

class NotesFragment : Fragment(R.layout.notes_fragment) {

    private lateinit var binding: NotesFragmentBinding
    private val adapter = NotesFragmentAdapter(
        { viewModel.removeItem(it) },
        { firstItem, secondItem -> viewModel.replaceItems(firstItem, secondItem) },
        { note -> viewModel.onItemClicked(note, parentFragmentManager) }
    )
    private val viewModel: NotesViewModel by viewModels {
        NotesViewModelFactory(NotesInteractor(NoteRepositoryImpl(requireContext())))
    }

    private var isIncreasing = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = NotesFragmentBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initLaunches()

        setFragmentResultListener(UPDATE_NOTES_REQUEST_KEY) { _, _ -> viewModel.requestNotes() }

        viewModel.requestNotes()
    }

    private fun initViews() {
        initRecyclerView()
        initClickListeners()
    }

    private fun initLaunches() {
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.notes.collect { list ->
                list?.let { adapter.submitList(it) }
            }
        }
    }

    private fun initClickListeners() {
        binding.addButton.setOnClickListener { viewModel.onAddClick(parentFragmentManager) }

        binding.filterButton.setOnClickListener {
            adapter.sortByPriority(isIncreasing)
            changeFilterIcon(isIncreasing)
            isIncreasing = isIncreasing.not()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        ItemTouchHelper(
            ItemTouchHelperCallback(
                { adapter.removeItem(it) },
                { positionFrom, positionTo -> adapter.moveItems(positionFrom, positionTo) },
                { positionFrom, positionTo -> adapter.replaceItems(positionFrom, positionTo) })
        ).attachToRecyclerView(binding.recyclerView)
    }

    private fun changeFilterIcon(isIncreasing: Boolean) {
        if (isIncreasing) {
            binding.filterButton.setImageResource(R.drawable.ic_baseline_arrow_downward_24)
        } else {
            binding.filterButton.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
        }
    }
}