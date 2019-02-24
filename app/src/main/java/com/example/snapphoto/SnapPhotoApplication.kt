package com.example.snapphoto

import android.app.Application
import android.util.Log
import timber.log.Timber

class SnapPhotoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("planted")
        }
    }
}