package com.example.snapphoto.ui.authentication.login

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.snapphoto.R
import com.example.snapphoto.databinding.LoginFragmentBinding
import com.example.snapphoto.ui.main.MainActivity

class LoginFragment : Fragment(), LoginFragmentNavigator {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding

    override fun startMainActivity() {
        //use this way to launch activity to clear task
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, LoginViewModelFactory(this))
            .get(LoginViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }
}
