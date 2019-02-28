package com.example.snapphoto.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.snapphoto.R
import com.example.snapphoto.ui.authentication.AuthenticationActivity
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
//        logOutButton.setOnClickListener {
//            mAuth.signOut()
//            startStartActivity()
//        }
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart: currentUser: ${mAuth.currentUser}")
        if (mAuth.currentUser == null) startStartActivity()
    }

    private fun startStartActivity() {
        //use this way to launch activity to clear task
        val intent = Intent(this, AuthenticationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
