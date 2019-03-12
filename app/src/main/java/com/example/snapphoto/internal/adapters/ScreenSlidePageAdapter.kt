package com.example.snapphoto.internal.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ScreenSlidePageAdapter(
    fragmentManager: FragmentManager,
    private val fragmentsList: List<Fragment>
): FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = fragmentsList[position]

    override fun getCount() = fragmentsList.size
}