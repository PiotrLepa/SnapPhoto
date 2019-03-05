package com.example.snapphoto.ui.view

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.snapphoto.R
import com.example.snapphoto.internal.FRAGMENT_CAMERA
import com.example.snapphoto.internal.FRAGMENT_FRIENDS
import kotlinx.android.synthetic.main.view_snapphoto_tabs.view.*
import timber.log.Timber

class SnapphotoTabsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

    private val mArgbEvaluator = ArgbEvaluator()
    private val mCenterColor = Color.WHITE
    private val mSlideColor = ContextCompat.getColor(context, R.color.colorLightGray)

    private var mSideWidgetsEndTranslationX = 0f
    private var mCenterWidgetsEndTranslationY = 0f
    private var mIndicatorEndTranslationX = 0f

    init {
        View.inflate(context, R.layout.view_snapphoto_tabs, this)
        captureImage.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val slideWidgetFromCenterDistance = (storiesImage.x - friendsImage.x) / 4
                mSideWidgetsEndTranslationX = slideWidgetFromCenterDistance - 40
                mIndicatorEndTranslationX = slideWidgetFromCenterDistance + 40
                mCenterWidgetsEndTranslationY = captureImage.y - savedPhotosImage.bottom + captureImage.height

                captureImage.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (position == FRAGMENT_FRIENDS) {
            setWidgetColor(1 - positionOffset)
            moveAndScaleSideWidgets(1 - positionOffset)
            moveAndScaleCenterWidgets(1 - positionOffset)
            moveAndScaleIndicatorWidget(positionOffset - 1)
            indicatorView.alpha = 1 - positionOffset
        } else if (position == FRAGMENT_CAMERA) {
            setWidgetColor(positionOffset)
            moveAndScaleSideWidgets(positionOffset)
            moveAndScaleCenterWidgets(positionOffset)
            moveAndScaleIndicatorWidget(positionOffset)
            indicatorView.alpha = positionOffset
        }
    }

    override fun onPageSelected(position: Int) {
    }

    fun setupWithViewPager(viewPager: ViewPager) {
        viewPager.addOnPageChangeListener(this)
    }

    private fun moveAndScaleSideWidgets(positionOffset: Float) {
        val translation = positionOffset * mSideWidgetsEndTranslationX
        friendsImage.translationX = translation
        storiesImage.translationX = -translation

        val scale = 1 - (positionOffset * .2).toFloat()
        friendsImage.scaleX = scale
        friendsImage.scaleY = scale

        storiesImage.scaleX = scale
        storiesImage.scaleY = scale
    }

    private fun moveAndScaleCenterWidgets(positionOffset: Float) {
        val translation = -positionOffset * mCenterWidgetsEndTranslationY
        captureImage.translationY = translation
        savedPhotosImage.translationY = translation * 2

        val scale = 1 - (positionOffset * .2).toFloat()
        captureImage.scaleX = scale
        captureImage.scaleY = scale
    }

    private fun moveAndScaleIndicatorWidget(positionOffset: Float) {
        indicatorView.translationX = positionOffset * mIndicatorEndTranslationX
        indicatorView.scaleX = positionOffset
    }

    private fun setWidgetColor(positionOffset: Float) {
        val color = mArgbEvaluator.evaluate(
            positionOffset,
            mCenterColor,
            mSlideColor
        ) as Int

        captureImage.setColorFilter(color)
        friendsImage.setColorFilter(color)
        storiesImage.setColorFilter(color)
        savedPhotosImage.setColorFilter(color)
    }
}