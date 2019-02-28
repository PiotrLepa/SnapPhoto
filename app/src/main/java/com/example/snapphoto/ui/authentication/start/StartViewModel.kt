package com.example.snapphoto.ui.authentication.start

import androidx.lifecycle.ViewModel;
import timber.log.Timber

class StartViewModel(
    private val startFragmentNavigator: StartFragmentNavigator
) : ViewModel() {

    fun logInButtonClicked() {
        Timber.d("logInButtonClicked: started")
        startFragmentNavigator.startLoginFragment()
    }

    fun signUpButtonClicked() {
        Timber.d("signUpButtonClicked: started")
        startFragmentNavigator.startRegistrationFragment()
    }
}
