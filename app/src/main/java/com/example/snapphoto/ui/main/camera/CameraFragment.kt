package com.example.snapphoto.ui.main.camera

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.snapphoto.R
import com.example.snapphoto.ui.authentication.AuthenticationActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.main_fragment.*

class CameraFragment : Fragment() {

    private lateinit var viewModel: CameraViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CameraViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
