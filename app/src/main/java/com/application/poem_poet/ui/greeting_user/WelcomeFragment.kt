package com.application.poem_poet.ui.greeting_user

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentWelcomeBinding
import com.application.poem_poet.ui.base.BaseFragment

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            welcomeEntranceBtn.setOnClickListener { findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment) }
            welcomeRegistrationBtn.setOnClickListener { findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment) }
        }
    }

    override fun initViewBinding() = FragmentWelcomeBinding.inflate(layoutInflater)
}