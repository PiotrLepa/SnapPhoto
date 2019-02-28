package com.example.snapphoto.ui.start.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapphoto.internal.FieldIsNotFilledException
import com.example.snapphoto.internal.await
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(
    private val loginFragmentNavigator: LoginFragmentNavigator
) : ViewModel() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    val emailTextInput = MutableLiveData<String>()
    val passwordTextInput = MutableLiveData<String>()

    private val _errorMessage = MutableLiveData<Exception>()
    val errorMessage: LiveData<Exception>
        get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun logInButtonClicked() {
        Timber.d("logInButtonClicked: started")
        launchSignInUser {
            Timber.d("logInButtonClicked: emailTextInput: ${emailTextInput.value}, passwordTextInput: ${passwordTextInput.value}")
            mAuth.signInWithEmailAndPassword(
                emailTextInput.value.toString(),
                passwordTextInput.value.toString()
            ).await()
            navigateToMainScreen()
        }
    }

    private fun launchSignInUser(block: suspend () -> Unit) {
        if (areFieldsEmpty()) {
            _errorMessage.value = FieldIsNotFilledException()
            return
        }
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                block()
            } catch (e: Exception) {
                Timber.e("launchSignInUser: Login failed: Exception: ${e.message}")
                _errorMessage.value = e
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun areFieldsEmpty() =
        emailTextInput.value.isNullOrEmpty()
                || passwordTextInput.value.isNullOrEmpty()

    private fun navigateToMainScreen() {
        Timber.d("navigateToMainScreen: started")
        loginFragmentNavigator.startMainActivity()
    }
}
