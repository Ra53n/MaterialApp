package com.example.materialapp.ui.view.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionManager
import coil.load
import com.example.materialapp.R
import com.example.materialapp.databinding.MainFragmentBinding
import com.example.materialapp.domain.repos.NasaRepositoryImpl
import com.example.materialapp.ui.viewmodel.MainViewModel
import com.example.materialapp.ui.viewmodel.MainViewModelFactory


class MainFragment : Fragment(R.layout.main_fragment) {

    private lateinit var binding: MainFragmentBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(NasaRepositoryImpl())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contextThemeWrapper: Context = ContextThemeWrapper(
            activity, activity?.theme
        )
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        return localInflater.inflate(R.layout.main_fragment, container, false)
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
        binding.fab.setOnClickListener { viewModel.onImageClick(parentFragmentManager) }
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
                binding.image.visibility = View.INVISIBLE
                url?.let { binding.image.load(it) }
                TransitionManager.beginDelayedTransition(binding.root, Fade())
                binding.image.visibility = View.VISIBLE
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