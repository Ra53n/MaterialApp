package com.example.materialapp.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import coil.load
import com.example.materialapp.R
import com.example.materialapp.databinding.MainFragmentBinding
import com.example.materialapp.domain.NasaRepositoryImpl


const val KEY_THEME = "KEY_THEME"

class MainFragment : Fragment(R.layout.main_fragment) {

    private lateinit var binding: MainFragmentBinding

    private var savedTheme: Int? = null

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(NasaRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        savedInstanceState?.getInt(KEY_THEME, R.style.Theme_EARTH)
            ?.let {
                savedTheme = it
                requireActivity().setTheme(it)
                setStatusBarColor()
            }
        super.onCreate(savedInstanceState)
    }

    private fun setStatusBarColor() {
        val typedValue = TypedValue()
        context?.theme?.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
        requireActivity().window.statusBarColor = typedValue.data
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
        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.earth -> {
                    savedTheme = R.style.Theme_EARTH
                }
                R.id.mars -> {
                    savedTheme = R.style.Theme_MARS
                }
                R.id.moon -> {
                    savedTheme = R.style.Theme_MOON
                }
            }
            requireActivity().recreate()
            true
        }

    }

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        val inflater = super.onGetLayoutInflater(savedInstanceState)
        val contextThemeWrapper: Context =
            ContextThemeWrapper(requireContext(), savedTheme ?: R.style.Theme_MaterialApp)
        return inflater.cloneInContext(contextThemeWrapper)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        savedTheme?.let {
            outState.putInt(KEY_THEME, it)
        }
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