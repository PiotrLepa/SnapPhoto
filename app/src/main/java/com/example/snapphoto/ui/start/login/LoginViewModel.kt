package com.example.snapphoto.ui.start.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import java.util.*
import kotlin.concurrent.schedule

class LoginViewModel : ViewModel() {

//    var isDataChecking = MutableLiveData<Boolean>().apply { value = true }
    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun logInButtonClicked() {
        Timber.d("logInButtonClicked: started")
        Timer("test", false).schedule(2000) {
            _isLoading.postValue(!_isLoading.value!!)
        }
    }
}
