package com.example.materialapp.ui.view.fragment

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import coil.load
import com.example.materialapp.R
import com.example.materialapp.databinding.DescriptionBottomSheetFragmentBinding
import com.example.materialapp.domain.data.PictureOfTheDayEntity
import com.example.materialapp.ui.view.utils.getColoredFirstSentence
import com.example.materialapp.ui.view.utils.getUnderlinedBoldText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DescriptionBottomSheetFragment(private val picture: PictureOfTheDayEntity) :
    BottomSheetDialogFragment() {

    private lateinit var binding: DescriptionBottomSheetFragmentBinding

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
        blurBackground()
    }

    override fun onDestroy() {
        super.onDestroy()
        unblurBackground()
    }

    private fun blurBackground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val blurEffect = RenderEffect.createBlurEffect(
                15f, 0f,
                Shader.TileMode.REPEAT
            )
            requireActivity().findViewById<CoordinatorLayout>(R.id.activity_container)
                ?.setRenderEffect(blurEffect)
        }
    }

    private fun unblurBackground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requireActivity().findViewById<CoordinatorLayout>(R.id.activity_container)
                ?.setRenderEffect(null)
        }
    }

    private fun bindViews() {
        binding.descriptionTitle.text = getUnderlinedBoldText(picture.title)
        binding.descriptionImage.load(picture.url)
        binding.descriptionText.text =
            getColoredFirstSentence(requireContext(), picture.explanation)
    }
}