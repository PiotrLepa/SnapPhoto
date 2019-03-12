package com.example.snapphoto.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.snapphoto.internal.FRAGMENT_CAMERA

class MainViewModel : ViewModel() {

    var currentPage = MutableLiveData<Int>(FRAGMENT_CAMERA)
}