package com.example.snapphoto.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.snapphoto.R
import com.example.snapphoto.databinding.ActivityMainBinding
import com.example.snapphoto.internal.*
import com.example.snapphoto.ui.authentication.AuthenticationActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = this

        mAuth = FirebaseAuth.getInstance()

        if (hasPermission()) {
            LifecycleBoundCameraManager(this, PreviewCameraManager(this, textureView))
        } else {
            requestPermission()
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart: currentUser: ${mAuth.currentUser}")
        if (mAuth.currentUser == null) startStartActivity()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Timber.d("onRequestPermissionsResult: size ${grantResults.size}")

        if (requestCode == CAMERA_AND_STORAGE_PERMISSION_CODE) {
            if (grantResults.size != 3
                || grantResults[0] != PackageManager.PERMISSION_GRANTED
                || grantResults[1] != PackageManager.PERMISSION_GRANTED
                || grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                //TODO if permission denied show error screen
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
            } else {
                // permission granted
                this.recreate() // restart activity to enable camera preview
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun startStartActivity() {
        // use this way to open authentication screen and clear back stack
        val intent = Intent(this, AuthenticationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun hasPermission(): Boolean {
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        + ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        + ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        return true
    }

    private fun requestPermission() {
        Timber.d("requestPermission: started")
        ActivityCompat.requestPermissions(this,
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE),
            CAMERA_AND_STORAGE_PERMISSION_CODE
        )
    }
}
