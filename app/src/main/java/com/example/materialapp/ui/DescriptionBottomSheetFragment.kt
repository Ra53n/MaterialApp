package com.example.materialapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.materialapp.R
import com.example.materialapp.api.PictureOfTheDayResponse
import com.example.materialapp.databinding.DescriptionBottomSheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DescriptionBottomSheetFragment(private val picture: PictureOfTheDayResponse) :
    BottomSheetDialogFragment() {

    lateinit var binding: DescriptionBottomSheetFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DescriptionBottomSheetFragmentBinding.bind(
            inflater.inflate(
                R.layout.description_bottom_sheet_fragment,
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
        binding.descriptionTitle.text = picture.title
        binding.descriptionImage.load(picture.url)
        binding.descriptionText.text = picture.explanation
    }
}