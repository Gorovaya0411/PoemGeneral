package com.application.poem_poet.ui.general_navigation.profile

import android.os.Bundle
import android.view.View
import com.application.poem_poet.MyApplication
import com.application.poem_poet.databinding.FragmentProfileBinding
import com.application.poem_poet.di.modul.ui.main.MainActivityModule
import com.application.poem_poet.ui.base.BaseFragment
import com.application.poem_poet.ui.main.MainActivity
import com.application.poem_poet.ui.main.MainActivityPresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ProfileFragment : BaseFragment<FragmentProfileBinding>(){


    var firebaseUser: FirebaseUser? = null
    private val contextActivity: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseUser = FirebaseAuth.getInstance().currentUser
        with(binding) {

        }
    }


    override fun initViewBinding() = FragmentProfileBinding.inflate(layoutInflater)
}