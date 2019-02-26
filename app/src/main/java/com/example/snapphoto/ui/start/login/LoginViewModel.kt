package com.example.snapphoto.ui.start.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapphoto.internal.await
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel : ViewModel(){

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    val emailTextInput = MutableLiveData<String>()
    val passwordTextInput = MutableLiveData<String>()

    private val _isLoginFailed = MutableLiveData<Boolean>(false)
    val isLoginFailed: LiveData<Boolean>
        get() = _isLoginFailed

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun logInButtonClicked() {
        Timber.d("logInButtonClicked: started")
        _isLoginFailed.value = false
        if (!areFieldsEmpty()) {
            _isLoading.value = true
            viewModelScope.launch {
                try {
                    val job = mAuth.signInWithEmailAndPassword(emailTextInput.value.toString(), passwordTextInput.value.toString()).await()
                    Timber.d("logInButtonClicked: job: $job")
                } catch (e: Exception) {
                    Timber.e("logInButtonClicked: Login Failed: Exception: ${e.message}")
                    _isLoginFailed.postValue(true)
                }
                _isLoading.postValue(false)
            }
        } else {
            _isLoginFailed.value = true
        }
    }

    private fun areFieldsEmpty() =
        emailTextInput.value.isNullOrEmpty() || passwordTextInput.value.isNullOrEmpty()

}
