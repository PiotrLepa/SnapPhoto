package com.example.snapphoto.ui.authentication.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class RegistrationViewModelFactory(
    private val registrationFragmentNavigator: RegistrationFragmentNavigator
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(registrationFragmentNavigator) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}