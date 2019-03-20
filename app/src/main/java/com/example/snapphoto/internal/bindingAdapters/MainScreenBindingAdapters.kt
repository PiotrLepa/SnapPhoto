package com.example.snapphoto.internal.bindingAdapters

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.databinding.*
import androidx.viewpager.widget.ViewPager
import androidx.databinding.adapters.ListenerUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.snapphoto.R
import com.example.snapphoto.internal.FRAGMENT_CAMERA
import com.example.snapphoto.internal.FRAGMENT_FRIENDS
import com.example.snapphoto.internal.USER_PROFILE_DIALOG_FRAGMENT
import com.example.snapphoto.internal.VERTICAL_FRAGMENT_SAVED_PHOTOS
import com.example.snapphoto.internal.adapters.ScreenSlidePageAdapter
import com.example.snapphoto.ui.main.camera.CameraFragmentDirections
import com.example.snapphoto.ui.userProfile.UserProfileDialogFragment
import com.example.snapphoto.ui.view.AutoAdjustToolbar
import com.example.snapphoto.ui.view.SnapphotoTabsView
import com.example.snapphoto.ui.view.VerticalViewPager
import timber.log.Timber


@BindingMethods(
    BindingMethod(type = ViewPager::class, attribute = "android:offscreenPageLimit", method = "setOffscreenPageLimit"),
    BindingMethod(type = ViewPager::class, attribute = "android:currentPage", method = "setCurrentItem"),
    BindingMethod(type = SnapphotoTabsView::class, attribute = "setupWithViewPager", method = "setupWithViewPager"),
    BindingMethod(type = AutoAdjustToolbar::class, attribute = "setupWithViewPager", method = "setupWithViewPager")
)
@InverseBindingMethods(
    InverseBindingMethod(type = ViewPager::class, attribute = "android:currentPage", method = "getCurrentItem")
)
object MainScreenBindingAdapters {

    @BindingAdapter("openDialogFragmentOnProfileImageClick")
    @JvmStatic fun openDialogFragmentOnProfileImageClick(toolbar: AutoAdjustToolbar, shouldOpen: Boolean) {
        if (!shouldOpen) return

        toolbar.setOnUserImageClickListener(View.OnClickListener {
            val fragmentManager = (toolbar.context as AppCompatActivity).supportFragmentManager
            val dialogFragment = UserProfileDialogFragment.newInstance()
            dialogFragment.show(fragmentManager, USER_PROFILE_DIALOG_FRAGMENT)
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    @BindingAdapter("interceptAllTouchEventsWhenOpen")
    @JvmStatic fun interceptAllTouchEventsWhenOpen(verticalViewPager: VerticalViewPager, currentPage: Int) {
        verticalViewPager.setOnTouchListener { v, event ->
            if (currentPage == VERTICAL_FRAGMENT_SAVED_PHOTOS) {
                if (event.action == MotionEvent.ACTION_DOWN && v is ViewGroup) {
                    v.requestDisallowInterceptTouchEvent(true)
                }
            }
            false
        }
    }

    @BindingAdapter("setupWithViewPager")
    @JvmStatic fun setupWithViewPager(backgroundView: View, viewPager: ViewPager) {
        Timber.d("setupWithViewPager: ")
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == FRAGMENT_FRIENDS) {
                    backgroundView.setBackgroundColor(ContextCompat.getColor(backgroundView.context, R.color.colorBlue))
                    backgroundView.alpha = 1 - positionOffset
                } else if (position == FRAGMENT_CAMERA) {
                    backgroundView.setBackgroundColor(ContextCompat.getColor(backgroundView.context, R.color.colorPurple))
                    backgroundView.alpha = positionOffset
                }
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }

    @BindingAdapter("fragments")
    @JvmStatic fun setFragments(viewPager: ViewPager, fragmentsList: List<Fragment>) {
        val fragmentManager = (viewPager.context as AppCompatActivity).supportFragmentManager
        viewPager.adapter = ScreenSlidePageAdapter(fragmentManager, fragmentsList)
    }

    @BindingAdapter(
        value = ["android:onPageScrolled", "android:onPageSelected", "android:onPageScrollStateChanged", "android:currentPageAttrChanged"],
        requireAll = false
    )
    @JvmStatic fun onSetListeners(
        pager: ViewPager, scrolled: OnPageScrolled?, selected: OnPageSelected?,
        scrollStateChanged: OnPageScrollStateChanged?, currentPageAttrChanged: InverseBindingListener?
    ) {

        val newValue: ViewPager.OnPageChangeListener?
        if (scrolled == null && selected == null && scrollStateChanged == null && currentPageAttrChanged == null) {
            newValue = null
        } else {
            newValue = object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    scrolled?.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    selected?.onPageSelected(position)
                    currentPageAttrChanged?.onChange()
                }

                override fun onPageScrollStateChanged(state: Int) {
                    scrollStateChanged?.onPageScrollStateChanged(state)
                }
            }
        }
        val oldValue = ListenerUtil.trackListener(pager, newValue, R.id.page_change_listener)
        if (oldValue != null) {
            pager.removeOnPageChangeListener(oldValue)
        }
        if (newValue != null) {
            pager.addOnPageChangeListener(newValue)
        }
    }

    interface OnPageScrolled {
        fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
    }

    interface OnPageSelected {
        fun onPageSelected(position: Int)
    }

    interface OnPageScrollStateChanged {
        fun onPageScrollStateChanged(state: Int)
    }
}