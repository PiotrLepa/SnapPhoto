package com.example.snapphoto.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.snapphoto.data.entity.User
import com.example.snapphoto.internal.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val DB_USERS = "users"

class SnapphotoRepositoryImpl : SnapphotoRepository {

//    private val _user = MutableLiveData<User>()
//    val user: LiveData<User>
//        get() = _user

    private val firestoreDatabase = FirebaseFirestore.getInstance()
    private val userRef = firestoreDatabase.collection(DB_USERS)

    override suspend fun saveUser(user: User) {
        withContext(Dispatchers.IO) {
            userRef.document(user.id).set(user)
        }
    }

    override suspend fun getUser(): LiveData<User> {
        val fetchedUser = MutableLiveData<User>()
        withContext(Dispatchers.IO) {
            val documentSnapshot = userRef.document(FirebaseAuth.getInstance().currentUser!!.uid)
                .get(Source.CACHE)
                .await()
            fetchedUser.postValue(documentSnapshot.toObject(User::class.java))
        }
        return fetchedUser
    }
}