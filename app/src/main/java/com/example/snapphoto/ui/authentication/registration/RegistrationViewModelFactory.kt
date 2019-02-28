package com.example.snapphoto.ui.authentication.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.snapphoto.data.repository.SnapphotoRepository
import java.lang.IllegalArgumentException

class RegistrationViewModelFactory(
    private val registrationFragmentNavigator: RegistrationFragmentNavigator,
    private val snapphotoRepository: SnapphotoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(registrationFragmentNavigator, snapphotoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}