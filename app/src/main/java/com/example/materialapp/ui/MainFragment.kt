package com.example.materialapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import coil.load
import com.example.materialapp.R
import com.example.materialapp.databinding.MainFragmentBinding
import com.example.materialapp.domain.NasaRepositoryImpl

class MainFragment : Fragment(R.layout.main_fragment) {

    private lateinit var binding: MainFragmentBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(NasaRepositoryImpl())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)

        setLaunches()
        initViews()
    }

    private fun initViews() {
        initWikiSearchListener()
        initChips()
    }

    private fun initWikiSearchListener() {
        binding.searchTextInputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse(resources.getString(R.string.wikipedia_endpoint) + binding.searchEditText.text.toString())
            })
        }
    }


    private fun setLaunches() {
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.image.collect { url ->
                url?.let { binding.image.load(it) }
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.title.collect { title ->
                title?.let { binding.title.text = title }
            }
        }
    }

    private fun initChips() {
        binding.chipNormalImage.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                viewModel.requestPictureOfTheDay(
                    false
                )
            }
        }
        binding.chipHDImage.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                viewModel.requestPictureOfTheDay(
                    true
                )
            }
        }
        binding.chipNormalImage.isChecked = true
    }
}