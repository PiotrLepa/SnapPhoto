package com.example.snapphoto.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.snapphoto.internal.FRAGMENT_CAMERA
import com.example.snapphoto.ui.main.camera.CameraFragment
import com.example.snapphoto.ui.main.friends.FriendsFragment
import com.example.snapphoto.ui.main.stories.StoriesFragment

class MainViewModel : ViewModel() {

    val fragmentsList = listOf(
        FriendsFragment(),
        CameraFragment(),
        StoriesFragment()
    )

    var currentPage = MutableLiveData<Int>(FRAGMENT_CAMERA)
}