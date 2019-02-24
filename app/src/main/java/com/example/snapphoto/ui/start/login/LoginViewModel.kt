package com.example.snapphoto.ui.start.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import timber.log.Timber
import java.util.*
import kotlin.concurrent.schedule

class LoginViewModel : ViewModel() {

    var isDataChecking = MutableLiveData<Boolean>().apply { value = true }

    fun logInButtonClicked() {
        Timber.d("logInButtonClicked: started")
        Timber.d("beforeVisibility: ${isDataChecking.value}")
        Timer("test", false).schedule(2000) {
            isDataChecking.postValue(!isDataChecking.value!!)
            Timber.d("afterVisibility: ${isDataChecking.value}")
        }
    }
}
