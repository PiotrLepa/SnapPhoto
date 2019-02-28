package com.example.snapphoto.ui.start.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapphoto.internal.FieldIsNotFilledException
import com.example.snapphoto.internal.PasswordsAreDifferentException
import com.example.snapphoto.internal.await
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import timber.log.Timber

class RegistrationViewModel(
    private val registrationFragmentNavigator: RegistrationFragmentNavigator
) : ViewModel() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    val emailTextInput = MutableLiveData<String>("")
    val usernameTextInput = MutableLiveData<String>("")
    val passwordTextInput = MutableLiveData<String>("")
    val confirmPasswordTextInput = MutableLiveData<String>("")

    private val _errorMessage = MutableLiveData<Exception>()
    val errorMessage: LiveData<Exception>
        get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    
    fun signUpButtonClicked() {
        Timber.d("onSignUpButtonClicked: started")
        launchCreateUser {
            Timber.d("signUpButtonClicked: emailTextInput: ${emailTextInput.value}, passwordTextInput: ${passwordTextInput.value}")
            mAuth.createUserWithEmailAndPassword(
                    emailTextInput.value.toString(),
                    passwordTextInput.value.toString()
            ).await()
            sendVerificationEmail()
            navigateToMainScreen()
        }
    }

    private fun launchCreateUser(block: suspend () -> Unit) {
        Timber.d("launchCreateUser: started")
        if (areFieldsEmpty()) {
            _errorMessage.value = FieldIsNotFilledException()
            return
        }
        if (arePasswordDifferent()) {
            _errorMessage.value = PasswordsAreDifferentException()
            return
        }
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                block()
            } catch (e: Exception) {
                Timber.e("launchCreateUser: Create new user failed: Exception: $e")
                _errorMessage.value = e
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun areFieldsEmpty() =
        emailTextInput.value.isNullOrEmpty()
                || usernameTextInput.value.isNullOrEmpty()
                || passwordTextInput.value.isNullOrEmpty()
                || confirmPasswordTextInput.value.isNullOrEmpty()

    private fun arePasswordDifferent() =
        passwordTextInput.value != confirmPasswordTextInput.value

    private suspend fun sendVerificationEmail() {
        Timber.d("sendVerificationEmail: started")
        mAuth.currentUser?.apply {
            sendEmailVerification().await()
        }
    }

    private fun navigateToMainScreen() {
        Timber.d("navigateToMainScreen: started")
        registrationFragmentNavigator.startMainActivity()
    }
}
