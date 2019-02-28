package com.example.snapphoto.data

import androidx.lifecycle.MutableLiveData
import com.example.snapphoto.data.entity.User

class SnapphotoDatabase {

    val user = MutableLiveData<User>()
}