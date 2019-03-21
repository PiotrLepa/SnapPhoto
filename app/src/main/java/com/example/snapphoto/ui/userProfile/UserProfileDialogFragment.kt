package com.example.snapphoto.ui.userProfile

import android.app.Dialog
import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout

import com.example.snapphoto.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import timber.log.Timber

class UserProfileDialogFragment : BottomSheetDialogFragment() {

    interface OnCreateDialogListener {
        fun onCreatedDialog()
    }
    private var onCreateDialogListener: OnCreateDialogListener?= null

    companion object {
        fun newInstance() = UserProfileDialogFragment()
    }

    private lateinit var viewModel: UserProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.user_profile_dialog_fragment, container, false)
        view.findViewById<ImageView>(R.id.hideScreenImage).setOnClickListener {
            dialog.dismiss()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
        // TODO: Use the ViewModel
        Timber.d("onActivityCreated: dialog: $dialog")
        setDialogToFullScreen()
        onCreateDialogListener?.onCreatedDialog()
        dialog.window.attributes.windowAnimations = R.style.UserProfileAnimator
    }

    fun setOnDialogCreateListener(dialogListener: OnCreateDialogListener) {
        onCreateDialogListener = dialogListener
    }

    private fun setDialogToFullScreen() {
        dialog ?: return
        val bottomSheet = dialog.findViewById<View>(R.id.design_bottom_sheet)
        bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        val view = view
        view?.post {
            val parentView = (view.parent) as View
            val params = (parentView.layoutParams) as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior
            bottomSheetBehavior.peekHeight = view.measuredHeight
            ((bottomSheet.parent) as View).setBackgroundColor(Color.TRANSPARENT)

//            bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//                override fun onSlide(view: View, positionOffSet: Float) {
//                }
//
//                override fun onStateChanged(view: View, state: Int) {
//                    if (state == BottomSheetBehavior.STATE_HIDDEN) {
//                        dismiss()
//                    }
//                }
//            })
        }
    }
}
