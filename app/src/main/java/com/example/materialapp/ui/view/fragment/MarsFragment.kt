package com.example.materialapp.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.materialapp.R
import com.example.materialapp.databinding.MarsFragmentBinding
import com.example.materialapp.domain.NasaRepositoryImpl
import com.example.materialapp.ui.view.adapter.MarsAdapter
import com.example.materialapp.ui.viewmodel.MarsViewModel
import com.example.materialapp.ui.viewmodel.MarsViewModelFactory

class MarsFragment : Fragment(R.layout.mars_fragment) {
    private lateinit var binding: MarsFragmentBinding
    private var adapter = MarsAdapter()
    private val viewModel: MarsViewModel by viewModels {
        MarsViewModelFactory(NasaRepositoryImpl())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MarsFragmentBinding.bind(view)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.requestMarsPhotos()

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.image.collect { list ->
                list?.let { adapter.setData(it) }
            }
        }
    }
}