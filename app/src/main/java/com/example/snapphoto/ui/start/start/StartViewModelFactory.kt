package com.example.snapphoto.ui.start.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.snapphoto.ui.start.FragmentNavigator
import java.lang.IllegalArgumentException

class StartViewModelFactory(
    private val fragmentNavigator: FragmentNavigator
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StartViewModel::class.java)) {
            return StartViewModel(fragmentNavigator) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}