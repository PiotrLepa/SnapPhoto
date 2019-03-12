package com.example.snapphoto.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager
import com.example.snapphoto.R
import com.example.snapphoto.internal.FRAGMENT_CAMERA
import com.example.snapphoto.internal.FRAGMENT_FRIENDS
import kotlinx.android.synthetic.main.toolbar_auto_adjust.view.*
import timber.log.Timber

class AutoAdjustToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

    private var mWidgetsMaxTranslationX = 0f

    init {
        View.inflate(context, R.layout.toolbar_auto_adjust, this)
        adjustToolbarToStatusBarSize()
        switchCameraLensImage.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                mWidgetsMaxTranslationX = (switchCameraLensImage.x - addFriendImage.x) * 2
                switchCameraLensImage.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (position == FRAGMENT_FRIENDS) {
            moveToFriendsFragment(1 - positionOffset)
        }
        if (position == FRAGMENT_CAMERA) {
            moveToStoriesFragment(positionOffset)
        }
    }

    override fun onPageSelected(position: Int) {
    }

    fun setupWithViewPager(viewpager: ViewPager) {
        viewpager.addOnPageChangeListener(this)
    }

    private fun adjustToolbarToStatusBarSize() {
        setOnApplyWindowInsetsListener { v, insets ->
            val statusBarSize = insets.systemWindowInsetTop
            Timber.d("adjustToolbarToStatusBarSize: statusBarSize: $statusBarSize")
            guideline.setGuidelineBegin(statusBarSize)
            insets
        }
    }

    private fun moveToFriendsFragment(positionOffset: Float) {
        switchCameraLensImage.translationX = positionOffset * mWidgetsMaxTranslationX
        cameraFlashImage.translationX = positionOffset * mWidgetsMaxTranslationX

        switchCameraLensImage.alpha = 1 - positionOffset * 2
        cameraFlashImage.alpha = switchCameraLensImage.alpha

        newMessageImage.translationX = positionOffset * mWidgetsMaxTranslationX
        newMessageImage.alpha = ((positionOffset - 0.5) * 2).toFloat()

        // fix bug that widget changed does not set translationX properly when
        // viewpager swipe from Fragment Stories to Fragment Friends
        addFriendImage.translationX = 0f
    }

    private fun moveToStoriesFragment(positionOffset: Float) {
        switchCameraLensImage.translationX = positionOffset * -mWidgetsMaxTranslationX
        cameraFlashImage.translationX = positionOffset * -mWidgetsMaxTranslationX

        switchCameraLensImage.alpha = 1 - positionOffset * 2
        cameraFlashImage.alpha = switchCameraLensImage.alpha

        Timber.d("moveToStoriesFragment: mWidgetsMaxTranslationX/2 ${mWidgetsMaxTranslationX/2}")
        addFriendImage.translationX = positionOffset * mWidgetsMaxTranslationX/2
    }
}