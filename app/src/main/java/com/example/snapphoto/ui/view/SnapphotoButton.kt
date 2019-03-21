package com.example.snapphoto.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.snapphoto.R
import kotlinx.android.synthetic.main.view_snapphoto_button.view.*

class SnapphotoButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val ERROR_COLOR = -535263
    }

    var icon = R.drawable.default_user
        set(value) {
            field = value
            iconImage.setImageResource(value)
        }

    var label = ""
        set(value) {
            field = value
            labelText.text = value
        }

    var action = R.drawable.ic_keyboard_arrow_down_black_24dp
        set(value) {
            field = value
            actionImage.setImageResource(value)
        }

    var iconColor: Int = ERROR_COLOR
        set(value) {
            field = value
            if (value != ERROR_COLOR) {
                iconImage.setColorFilter(value)
            }
        }

    init {
        View.inflate(context, R.layout.view_snapphoto_button, this)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SnapphotoButton,
            0, 0).apply {
            try {
                initAttrs(this)
            } finally {
                recycle()
            }
        }
    }

    private fun initAttrs(typedArray: TypedArray) {
        typedArray.apply {
            icon = getResourceId(R.styleable.SnapphotoButton_iconImage, R.drawable.default_user)
            label = getString(R.styleable.SnapphotoButton_labelText) ?: ""
            action = getResourceId(R.styleable.SnapphotoButton_actionImage, R.drawable.ic_keyboard_arrow_down_black_24dp)
            iconColor = getColor(R.styleable.SnapphotoButton_iconColor, ERROR_COLOR)
        }
    }
}