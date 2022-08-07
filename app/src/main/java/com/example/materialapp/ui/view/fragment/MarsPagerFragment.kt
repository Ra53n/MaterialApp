package com.example.materialapp.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.materialapp.R
import com.example.materialapp.domain.data.CameraName
import com.example.materialapp.ui.view.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MarsPagerFragment : Fragment(R.layout.mars_pager_fragment) {

    private val values = CameraName.values()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pager: ViewPager2 = view.findViewById(R.id.view_pager)

        pager.adapter = ViewPagerAdapter(this)

        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)

        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = values[position].value
        }.attach()
    }
}