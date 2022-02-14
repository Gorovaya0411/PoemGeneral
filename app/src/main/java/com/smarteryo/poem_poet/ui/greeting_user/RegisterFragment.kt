package com.smarteryo.poem_poet.ui.greeting_user

import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.smarteryo.poem_poet.databinding.FragmentRegisterBinding
import com.smarteryo.poem_poet.ui.base.BaseFragment
import com.smarteryo.poem_poet.ui.main.MainActivity

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val contextActivity: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
//            buttonEntrance.setOnClickListener { findNavController().navigate(R.id.action_pauseFragment_to_menuFragment) }
//            buttonRegistration.setOnClickListener {  findNavController().navigate(R.id.action_pauseFragment_to_menuFragment) }
        }


    }

    override fun initViewBinding() = FragmentRegisterBinding.inflate(layoutInflater)
}