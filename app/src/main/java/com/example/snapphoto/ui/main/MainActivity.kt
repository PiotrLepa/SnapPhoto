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
import androidx.viewpager.widget.ViewPager
import com.example.snapphoto.R
import com.example.snapphoto.internal.CAMERA_AND_STORAGE_PERMISSION_CODE
import com.example.snapphoto.internal.FRAGMENT_CAMERA
import com.example.snapphoto.internal.FRAGMENT_FRIENDS
import com.example.snapphoto.internal.PreviewCameraManager
import com.example.snapphoto.ui.authentication.AuthenticationActivity
import com.example.snapphoto.ui.main.friends.FriendsFragment
import com.example.snapphoto.ui.main.stories.StoriesFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var previewCameraManager: PreviewCameraManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        previewCameraManager = PreviewCameraManager(this, textureView)

        setupViewPager()
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
                //TODO if permission denied show error screen
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun setupViewPager() {
        val fragmentsList = listOf(
            FriendsFragment(),
            Fragment(), //empty fragment
            StoriesFragment()
        )

        val pagerAdapter = ScreenSlidePageAdapter(supportFragmentManager, fragmentsList)
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = 2
        viewPager.currentItem = FRAGMENT_CAMERA

        tabsView.setupWithViewPager(viewPager)
        autoAdjustToolbar.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == FRAGMENT_FRIENDS) {
                    backgroundView.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.colorBlue))
                    backgroundView.alpha = 1 - positionOffset
                } else if (position == FRAGMENT_CAMERA) {
                    backgroundView.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.colorPurple))
                    backgroundView.alpha = positionOffset
                }
            }

            override fun onPageSelected(position: Int) {
            }
        })
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
