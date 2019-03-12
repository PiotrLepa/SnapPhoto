package com.example.snapphoto.ui.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.snapphoto.internal.PreviewCameraManager
import timber.log.Timber

class LifecycleBoundCameraManager(
    lifecycleOwner: LifecycleOwner,
    private val previewCameraManager: PreviewCameraManager
) : LifecycleObserver {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resumeCamera() {
        previewCameraManager.onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pauseCamera() {
        previewCameraManager.onPause()
    }
}