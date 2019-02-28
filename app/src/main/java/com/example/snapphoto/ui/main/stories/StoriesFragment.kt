package com.example.snapphoto.ui.main.stories

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
import kotlinx.android.synthetic.main.stories_fragment.*

class StoriesFragment : Fragment() {

    companion object {
        fun newInstance() = StoriesFragment()
    }

    private lateinit var viewModel: StoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.stories_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StoriesViewModel::class.java)

        logOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startStartActivity()
        }
    }

    private fun startStartActivity() {
        //use this way to launch activity to clear task
        val intent = Intent(context, AuthenticationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
