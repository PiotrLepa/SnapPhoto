package com.example.snapphoto.internal.bindingAdapters

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.*
import androidx.viewpager.widget.ViewPager
import androidx.databinding.adapters.ListenerUtil
import androidx.fragment.app.Fragment
import com.example.snapphoto.R
import com.example.snapphoto.internal.FRAGMENT_CAMERA
import com.example.snapphoto.internal.FRAGMENT_FRIENDS
import com.example.snapphoto.internal.adapters.ScreenSlidePageAdapter
import com.example.snapphoto.ui.main.friends.FriendsFragment
import com.example.snapphoto.ui.main.stories.StoriesFragment
import com.example.snapphoto.ui.view.AutoAdjustToolbar
import com.example.snapphoto.ui.view.SnapphotoTabsView


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

//    @JvmStatic fun onWidgetClicked(view: SnapphotoTabsView, listenerBlock: () -> Unit) {
//        when (view.id) {
//            R.id.friendsImage -> null
//            R.id.captureImage -> null
//            R.id.storiesImage -> null
//            R.id.savedPhotosImage -> return
//        }
//    }



    @BindingAdapter("setupWithViewPager")
    @JvmStatic fun setupWithViewPager(backgroundView: View, viewPager: ViewPager) {
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
    @JvmStatic fun setFragments(viewPager: ViewPager, context: Context) {
        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
        val fragmentsList = listOf(
            FriendsFragment(),
            Fragment(),
            StoriesFragment()
        )
        viewPager.adapter =
            ScreenSlidePageAdapter(fragmentManager, fragmentsList)
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