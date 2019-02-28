package com.example.snapphoto.data.repository

import androidx.lifecycle.LiveData
import com.example.snapphoto.data.entity.User

interface SnapphotoRepository {

    suspend fun saveUser(user: User)
    suspend fun getUser(): LiveData<User>
}