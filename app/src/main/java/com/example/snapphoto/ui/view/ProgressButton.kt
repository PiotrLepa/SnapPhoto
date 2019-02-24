package com.example.snapphoto.ui.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.snapphoto.R
import kotlinx.android.synthetic.main.progress_button.view.*
import timber.log.Timber
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.view.ViewOutlineProvider


class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var text = "Default"
        set(value) {
            field = value
            labelText.text = value}
    var textColor = Color.GRAY
        set(value) {
            field = value
            labelText.setTextColor(textColor)
        }
    var textSize = 15f
        set(value) {
            field = value
            labelText.textSize = textSize
        }
    var textAllCaps = false
        set(value) {
            field = value
            if (textAllCaps) text = text.toUpperCase() }
    var textStyle = Typeface.NORMAL
        set(value) {
            field = value
            labelText.typeface = Typeface.create(Typeface.DEFAULT, value)
        }
    var cornerRadius = 0f
        set(value) {
            field = value
//            clipToOutline = true
//            outlineProvider = RoundedOutlineProvider(120f)
            roundCorners()
        }
    var pressedColor = Color.BLACK
    var pressedText = "Pressed"
    var progressBarVisibility = false
        set(visibility) {
            Timber.d("setter started")
            field = visibility
            if (visibility) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.GONE
        }
        get() = true

    init {
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
        roundCorners()
    }

    private fun initAttrs(typedArray: TypedArray) {
        typedArray.apply {
            text = getString(R.styleable.ProgressButton_android_text) ?: "Default"
            textColor = getColor(R.styleable.ProgressButton_android_textColor, Color.GRAY)
            textSize = getDimension(R.styleable.ProgressButton_android_textSize, 15f)
            textAllCaps = getBoolean(R.styleable.ProgressButton_android_textAllCaps, false)
            textStyle = getInt(R.styleable.ProgressButton_android_textStyle, Typeface.NORMAL)
            cornerRadius = getDimension(R.styleable.ProgressButton_cornerRadius, 0f)
            pressedColor = getColor(R.styleable.ProgressButton_pressColor, Color.BLACK)
            pressedText = getString(R.styleable.ProgressButton_pressText) ?: "Pressed"
            progressBarVisibility = getBoolean(R.styleable.ProgressButton_progressBarVisibility, false)
        }
    }

    private fun roundCorners() {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadius = cornerRadius
        shape.color = ColorStateList.valueOf(Color.BLUE)
        this.background = shape
    }

    class RoundedOutlineProvider(private val radius: Float) : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            if (view != null && outline != null) {
                outline.setRoundRect(0, 0, view.width, view.height, radius)
            }
        }
    }
}