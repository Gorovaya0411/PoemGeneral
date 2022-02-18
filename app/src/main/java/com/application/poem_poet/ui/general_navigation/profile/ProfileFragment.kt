package com.application.poem_poet.ui.general_navigation.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentProfileBinding
import com.application.poem_poet.model.User
import com.application.poem_poet.ui.base.BaseFragment
import com.application.poem_poet.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import moxy.MvpAppCompatFragment

class ProfileFragment : MvpAppCompatFragment(),ProfileView {
    private val profilePresenter by moxyPresenter { ProfilePresenter() }

//    private val forOut = ForOutDialog(::logOut)

    lateinit var username: String
    lateinit var uid: String
    private var firebaseUser: FirebaseUser? = null
    private val contextActivity: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseUser = FirebaseAuth.getInstance().currentUser

    }

    override fun showElementsProfile(model: User?) {
        TODO("Not yet implemented")
    }

    override fun workWithStatus() {
        TODO("Not yet implemented")
    }

    override fun workWithAddress() {
        TODO("Not yet implemented")
    }

    override fun workWithAvatar(model: User?) {
        TODO("Not yet implemented")
    }

    override fun showDialog(model: DialogFragment) {
        TODO("Not yet implemented")
    }

    override fun openAddActivity() {
        TODO("Not yet implemented")
    }
}