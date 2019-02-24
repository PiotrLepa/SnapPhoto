package com.example.snapphoto.ui.start.start

import androidx.lifecycle.ViewModel;
import com.example.snapphoto.ui.start.FragmentNavigator
import timber.log.Timber

class StartViewModel(
    private val fragmentNavigator: FragmentNavigator
) : ViewModel() {

    fun logInButtonClicked() {
        Timber.d("logInButtonClicked: started")
        fragmentNavigator.startLoginFragment()
    }

    fun signUpButtonClicked() {
        Timber.d("signUpButtonClicked: started")
        fragmentNavigator.startRegistrationFragment()
    }
}
