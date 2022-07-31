package com.example.materialapp.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.materialapp.R
import com.example.materialapp.databinding.MarsFragmentBinding
import com.example.materialapp.domain.data.CameraName
import com.example.materialapp.domain.repos.NasaRepositoryImpl
import com.example.materialapp.ui.view.adapter.MarsAdapter
import com.example.materialapp.ui.viewmodel.MarsViewModel
import com.example.materialapp.ui.viewmodel.MarsViewModelFactory


const val CAMERA_NAME_KEY = "CAMERA_NAME_KEY"

class MarsFragment() : Fragment(R.layout.mars_fragment) {
    private lateinit var cameraName: CameraName
    private lateinit var binding: MarsFragmentBinding
    private var adapter = MarsAdapter()
    private val viewModel: MarsViewModel by viewModels {
        MarsViewModelFactory(NasaRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.getSerializable(CAMERA_NAME_KEY)?.let { name ->
            cameraName = name as CameraName
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MarsFragmentBinding.bind(view)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.requestMarsPhotos(cameraName)

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.image.collect { list ->
                list?.let { adapter.setData(it) }
            }
        }
    }

    companion object {
        fun newInstance(cameraName: CameraName): MarsFragment {
            val fragment = MarsFragment()
            val bundle = Bundle().apply { putSerializable(CAMERA_NAME_KEY, cameraName) }
            fragment.arguments = bundle
            return fragment
        }
    }
}