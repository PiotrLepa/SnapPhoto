package com.example.snapphoto.internal.bindingAdapters

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.lifecycle.LiveData
import androidx.viewpager.widget.ViewPager
import com.example.snapphoto.R
import com.example.snapphoto.internal.FieldIsNotFilledException
import com.example.snapphoto.internal.PasswordsAreDifferentException
import com.example.snapphoto.ui.view.AutoAdjustToolbar
import com.example.snapphoto.ui.view.ProgressButton
import com.example.snapphoto.ui.view.SnapphotoTabsView
import timber.log.Timber

object AuthenticationScreenBindingAdapters {

    @BindingAdapter(value = ["hideKeyboardOnClick", "customOnClick"], requireAll = false)
    @JvmStatic fun hideKeyboardOnClick(view: ProgressButton, enabled: Boolean?, listener: () -> Unit) {
        Timber.d("hideKeyboardOnClick: started")
        view.setOnClickListener {
            listener()
            if (enabled != null && enabled) {
                val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE)
                        as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

    @BindingAdapter("errorMessage")
    @JvmStatic fun setErrorMessage(view: TextView, exception: Exception?) {
        Timber.d("setErrorMessage: error: $exception")
        val errorMessage: String? = when (exception) {
            is FieldIsNotFilledException -> view.context.getString(R.string.please_fill_all_fields)
            is PasswordsAreDifferentException -> view.context.getString(R.string.passwords_are_different)
            else -> exception?.message
        }
        view.text = errorMessage
    }
}