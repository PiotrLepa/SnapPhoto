package com.example.snapphoto.ui.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class LoginViewModelFactory(
    private val loginFragmentNavigator: LoginFragmentNavigator
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(loginFragmentNavigator) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}