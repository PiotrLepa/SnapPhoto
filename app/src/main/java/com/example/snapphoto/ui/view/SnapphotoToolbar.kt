package com.example.snapphoto.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.snapphoto.R
import kotlinx.android.synthetic.main.toolbar_snapphoto.view.*

class SnapphotoToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.toolbar_snapphoto, this)
        adjustToolbarToStatusBarSize()
    }

    private fun adjustToolbarToStatusBarSize() {
        setOnApplyWindowInsetsListener { v, insets ->
            val statusBarSize = insets.systemWindowInsetTop
            guideline.setGuidelineBegin(statusBarSize)
            insets
        }
    }
}