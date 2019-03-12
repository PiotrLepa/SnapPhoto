package com.example.snapphoto.ui.main.camera

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.snapphoto.R
import com.example.snapphoto.databinding.CameraFragmentBinding
import com.example.snapphoto.internal.adapters.ScreenSlidePageAdapter
import com.example.snapphoto.ui.main.friends.FriendsFragment
import kotlinx.android.synthetic.main.camera_fragment.*


class CameraFragment : Fragment() {

    private lateinit var viewModel: CameraViewModel
    private lateinit var binding: CameraFragmentBinding

    private val mFragmentsList = listOf(
        Fragment(), // transparent fragment
        FriendsFragment()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.camera_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CameraViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setupVerticalViewPager()
    }

    private fun setupVerticalViewPager() {
        val adapter = ScreenSlidePageAdapter(childFragmentManager, mFragmentsList)
        verticalViewPager.adapter = adapter
    }
}
