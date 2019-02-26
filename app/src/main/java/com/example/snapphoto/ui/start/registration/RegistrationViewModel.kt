package com.example.snapphoto.ui.start.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth

class RegistrationViewModel : ViewModel() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    val emailTextInput = MutableLiveData<String>()
    val usernameTextInput = MutableLiveData<String>()
    val passwordTextInput = MutableLiveData<String>()
    val confirmPasswordTextInput = MutableLiveData<String>()

    private val _isLoginFailed = MutableLiveData<Boolean>(false)
    val isLoginFailed: LiveData<Boolean>
        get() = _isLoginFailed

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading
}
