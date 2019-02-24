package com.example.snapphoto.ui.start.start

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.example.snapphoto.R
import com.example.snapphoto.databinding.StartFragmentBinding
import com.example.snapphoto.ui.start.FragmentNavigator

class StartFragment : Fragment(), FragmentNavigator {

    private lateinit var viewModel: StartViewModel
    private lateinit var binding: StartFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.start_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, StartViewModelFactory(this)).get(StartViewModel::class.java)
        binding.viewmodel = viewModel
    }

    override fun startLoginFragment() {
        val action = StartFragmentDirections.actionStartFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    override fun startRegistrationFragment() {
        val action = StartFragmentDirections.actionStartFragmentToRegistrationFragment()
        findNavController().navigate(action)
    }
}
