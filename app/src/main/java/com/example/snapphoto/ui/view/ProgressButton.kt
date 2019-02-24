package com.example.snapphoto.ui.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.progress_button.view.*
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.example.snapphoto.R
import timber.log.Timber
import android.graphics.drawable.RotateDrawable
import android.graphics.drawable.LayerDrawable




class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var text = ""
        set(value) {
            field = value
            labelText.text = value}

    private var textColor = Color.GRAY
        set(value) {
            field = value
            labelText.setTextColor(textColor)
        }

    private var textSize = 15f
        set(value) {
            field = value
            labelText.textSize = textSize
        }

    private var textAllCaps = false
        set(value) {
            field = value
            labelText.isAllCaps = value
            }

    private var textStyle = Typeface.NORMAL
        set(value) {
            field = value
            labelText.typeface = Typeface.create(Typeface.DEFAULT, value)
        }

    var startLoading = false
        set(value) {
            field = value
            if (value) {
                progressBarVisibility = true
                labelText.text = onLoadingText
                setBackgroundGradientDrawable()
            } else {
                progressBarVisibility = false
                labelText.text = text
                setBackgroundGradientDrawable()
            }
        }

    private var progressBarVisibility = false
        set(visibility) {
            field = visibility
            if (visibility) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.GONE
        }

    private var cornerRadius = 0f
    private var defaultBackgroundColor = Color.LTGRAY
    private var onPressBackgroundColor = Color.LTGRAY
    private var onLoadingBackgroundColor = Color.LTGRAY
    private var onLoadingText = ""

    companion object {
        private val states = arrayOf(
            intArrayOf(android.R.attr.state_pressed),
            intArrayOf(android.R.attr.state_enabled)
        )
    }

    init {
        Timber.d("init: started")
        View.inflate(context, R.layout.progress_button, this)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressButton,
            0 ,0).apply {
            try {
                initAttrs(this)
            } finally {
                recycle()
            }
        }
        setBackgroundGradientDrawable()
    }

    private fun initAttrs(typedArray: TypedArray) {
        typedArray.apply {
            Timber.d("initAttrs: started")
            text = getString(R.styleable.ProgressButton_android_text) ?: ""
            textColor = getColor(R.styleable.ProgressButton_android_textColor, Color.LTGRAY)
            textSize = getDimension(R.styleable.ProgressButton_android_textSize, 15f)
            textAllCaps = getBoolean(R.styleable.ProgressButton_android_textAllCaps, false)
            textStyle = getInt(R.styleable.ProgressButton_android_textStyle, Typeface.NORMAL)
            cornerRadius = getDimension(R.styleable.ProgressButton_cornerRadius, 0f)
            defaultBackgroundColor = getColor(R.styleable.ProgressButton_backgroundColor, Color.LTGRAY)
            onPressBackgroundColor = getColor(R.styleable.ProgressButton_onPressBackgroundColor, Color.LTGRAY)
            onLoadingBackgroundColor = getColor(R.styleable.ProgressButton_onLoadingBackgroundColor, Color.LTGRAY)
            onLoadingText = getString(R.styleable.ProgressButton_onLoadingText) ?: ""
            startLoading = getBoolean(R.styleable.ProgressButton_startLoading, false)
        }
    }
    private fun setBackgroundGradientDrawable() {
        Timber.d("setBackgroundGradientDrawable: started")
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadius = cornerRadius

        val colors = intArrayOf(onPressBackgroundColor, defaultBackgroundColor)
        shape.color = ColorStateList(states, colors)

        this.background = shape
    }
}