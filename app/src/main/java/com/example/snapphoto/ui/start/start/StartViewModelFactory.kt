package com.example.snapphoto.ui.start.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class StartViewModelFactory(
    private val startFragmentNavigator: StartFragmentNavigator
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StartViewModel::class.java)) {
            return StartViewModel(startFragmentNavigator) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}