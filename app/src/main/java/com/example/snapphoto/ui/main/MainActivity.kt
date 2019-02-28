package com.example.snapphoto.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.snapphoto.R
import com.example.snapphoto.internal.PreviewCameraManager
import com.example.snapphoto.ui.authentication.AuthenticationActivity
import com.example.snapphoto.ui.main.friends.FriendsFragment
import com.example.snapphoto.ui.main.stories.StoriesFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

const val FRAGMENT_FRIENDS = 0
const val FRAGMENT_CAMERA = 1
const val FRAGMENT_STORIES = 2

class MainActivity : AppCompatActivity() {

    companion object {
        private const val CAMERA_AND_STORAGE_PERMISSION_CODE = 8343
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var previewCameraManager: PreviewCameraManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
//        logOutButton.setOnClickListener {
//            mAuth.signOut()
//            startStartActivity()
//        }

        previewCameraManager = PreviewCameraManager(this, textureView)

        val fragmentsList = listOf(
            FriendsFragment(),
            Fragment(),
            StoriesFragment()
        )

        val pagerAdapter = ScreenSlidePageAdapter(supportFragmentManager, fragmentsList)
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = 2
        viewPager.currentItem = FRAGMENT_CAMERA
    }


    override fun onStart() {
        super.onStart()
        Timber.d("onStart: currentUser: ${mAuth.currentUser}")
        if (mAuth.currentUser == null) startStartActivity()
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume: hasPermission: ${hasPermission()}")
        if (hasPermission()) previewCameraManager.onResume()
        else requestPermission()
    }

    override fun onPause() {
        super.onPause()
        if (hasPermission()) previewCameraManager.onPause()
        else requestPermission()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Timber.d("onRequestPermissionsResult: size ${grantResults.size}")

        if (requestCode == CAMERA_AND_STORAGE_PERMISSION_CODE) {
            if (grantResults.size != 3
                || grantResults[0] != PackageManager.PERMISSION_GRANTED
                || grantResults[1] != PackageManager.PERMISSION_GRANTED
                || grantResults[2] != PackageManager.PERMISSION_GRANTED) {
//                ErrorDialog.newInstance(getString(R.string.request_permission))
//                    .show(childFragmentManager, FRAGMENT_DIALOG)
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun startStartActivity() {
        //use this way to launch activity to clear task
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

    private class ScreenSlidePageAdapter(
        fragmentManager: FragmentManager,
        private val fragmentsList: List<Fragment>
    ): FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int) = fragmentsList[position]

        override fun getCount() = fragmentsList.size
    }
}
