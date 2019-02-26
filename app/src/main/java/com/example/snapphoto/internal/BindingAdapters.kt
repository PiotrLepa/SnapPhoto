package com.example.snapphoto.internal

import com.google.android.material.textfield.TextInputLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData


object BindingAdapters {

    @BindingAdapter("errorText")
    @JvmStatic fun setErrorMessage(view: TextInputLayout, errorMessage: LiveData<String>) {
        errorMessage.value?.let {
            if (view.error != errorMessage.value) view.error = errorMessage.value
        }
    }
}