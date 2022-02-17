package com.application.poem_poet.ui.general_navigation.profile

import android.os.Bundle
import android.view.View
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentProfileBinding
import com.application.poem_poet.ui.base.BaseFragment
import com.application.poem_poet.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile){


    private var firebaseUser: FirebaseUser? = null
    private val contextActivity: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseUser = FirebaseAuth.getInstance().currentUser
        with(binding) {
        }
    }
}