package com.example.materialapp.ui.view.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.transition.*
import coil.load
import com.example.materialapp.R
import com.example.materialapp.databinding.MainFragmentBinding
import com.example.materialapp.domain.repos.NasaRepositoryImpl
import com.example.materialapp.ui.view.utils.SearchRequestValidator
import com.example.materialapp.ui.view.utils.getBoldText
import com.example.materialapp.ui.viewmodel.MainViewModel
import com.example.materialapp.ui.viewmodel.MainViewModelFactory


class MainFragment : Fragment(R.layout.main_fragment) {

    private lateinit var binding: MainFragmentBinding
    private var isExpanded = false

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
        binding.image.setOnClickListener { zoomImageTransition() }
    }

    private fun zoomImageTransition() {
        isExpanded = isExpanded.not()

        TransitionManager.beginDelayedTransition(
            binding.root,
            TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeImageTransform())
        )

        val params: ViewGroup.LayoutParams = binding.image.layoutParams

        params.width = getLayoutParams(isExpanded)

        params.height = getLayoutParams(isExpanded)

        binding.image.layoutParams = params
    }

    private fun getLayoutParams(isExpanded: Boolean) =
        if (isExpanded) {
            ViewGroup.LayoutParams.MATCH_PARENT
        } else {
            ViewGroup.LayoutParams.WRAP_CONTENT
        }

    private fun initWikiSearchListener() {
        binding.searchTextInputLayout.setEndIconOnClickListener {

            val searchRequest = binding.searchEditText.text.toString()
            if (SearchRequestValidator.isValid(searchRequest)) {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data =
                        Uri.parse(
                            resources.getString(R.string.wikipedia_endpoint) + searchRequest
                        )
                }
                startActivity(intent)
            } else {
                Toast.makeText(
                    context,
                    resources.getString(R.string.incorrect_request),
                    Toast.LENGTH_SHORT
                ).show()
            }
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
            viewModel.title.collect { titleText ->
                titleText?.let {
                    binding.title.text = getBoldText(titleText)
                }
            }
        }
    }

    private fun initChips() {
        binding.chipNormalImage.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.requestPictureOfTheDay(
                    false
                )
            }
        }
        binding.chipHDImage.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.requestPictureOfTheDay(
                    true
                )
            }
        }
        binding.chipNormalImage.isChecked = true
    }
}