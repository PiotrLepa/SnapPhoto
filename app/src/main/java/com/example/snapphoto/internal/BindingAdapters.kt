package com.example.snapphoto.internal

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.example.snapphoto.R
import com.example.snapphoto.ui.view.ProgressButton
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import timber.log.Timber


object BindingAdapters {

    @BindingAdapter("errorText")
    @JvmStatic fun setErrorMessage(view: TextInputLayout, errorMessage: LiveData<String>) {
        errorMessage.value?.let {
            if (view.error != errorMessage.value) view.error = errorMessage.value
        }
    }

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
        var errorMessage: String?
        when (exception) {
            is FieldIsNotFilledException -> errorMessage = view.context.getString(R.string.please_fill_all_fields)
            is PasswordsAreDifferentException -> errorMessage = view.context.getString(R.string.passwords_are_different)
            else -> errorMessage = exception?.message
        }
        view.text = errorMessage
    }
}