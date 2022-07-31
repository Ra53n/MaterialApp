package com.example.materialapp.ui.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.materialapp.domain.data.CameraName
import com.example.materialapp.ui.view.fragment.MarsFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val cameraNames = CameraName.values()


    override fun getItemCount() = cameraNames.size

    override fun createFragment(position: Int) = MarsFragment.newInstance(cameraNames[position])
}
