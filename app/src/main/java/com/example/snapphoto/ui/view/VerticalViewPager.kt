package com.example.snapphoto.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager

class VerticalViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    init {
        setPageTransformer(true, VerticalPageTransformer())
        overScrollMode = View.OVER_SCROLL_NEVER
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        return false
    }

    override fun canScrollVertically(direction: Int): Boolean {
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val intercepted = super.onInterceptTouchEvent(swapXY(ev))
        swapXY(ev)
        return intercepted
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return super.onTouchEvent(swapXY(ev))
    }

    private fun swapXY(motionEvent: MotionEvent?) : MotionEvent? {
        motionEvent ?: return motionEvent

        val newX = (motionEvent.y / height) * width
        val newY = (motionEvent.x / width) * height

        motionEvent.setLocation(newX, newY)
        return motionEvent
    }

    private class VerticalPageTransformer : ViewPager.PageTransformer {

        override fun transformPage(page: View, position: Float) {
            when {
                position < -1 -> page.alpha = 0f
                position <= 1 -> {
                    page.alpha = 1f
                    page.translationX = page.width * -position
                    val yPosition = position * page.height
                    page.translationY = yPosition
                }
                else -> page.alpha = 0f
            }
        }
    }
}