package com.application.poem_poet.ui.greeting_user

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentWelcomeBinding
import com.application.poem_poet.ui.base.BaseFragment
import com.application.poem_poet.ui.community.CommunityActivity
import com.application.poem_poet.ui.main.MainActivity
import com.application.poem_poet.utill.extension.launchActivityWithFinish
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    var firebaseUser: FirebaseUser? = null
    private val contextActivity: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseUser = FirebaseAuth.getInstance().currentUser
        with(binding) {
            welcomeEntranceBtn.setOnClickListener { findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment) }
            welcomeRegistrationBtn.setOnClickListener { findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment) }
        }
    }

    override fun onStart() {
        super.onStart()

        if (firebaseUser != null) {
            launchActivityWithFinish<CommunityActivity>(contextActivity)
        }
    }

    override fun initViewBinding() = FragmentWelcomeBinding.inflate(layoutInflater)
}