package com.application.poem_poet.ui.general_navigation.poets

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentGeneralPoetsBinding
import com.application.poem_poet.ui.base.BaseFragment
import com.application.poem_poet.ui.community.CommunityActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class GeneralPoetsFragment : BaseFragment<FragmentGeneralPoetsBinding>() {

    private var firebaseUser: FirebaseUser? = null
    private val contextActivity: CommunityActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as CommunityActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        NavigationUI.setupWithNavController(
            binding.generalPoetsBottomNavigationView,
            Navigation.findNavController(
                contextActivity,
                R.id.general_poets_fragment_container_view
            )
        )
    }

    override fun initViewBinding() = FragmentGeneralPoetsBinding.inflate(layoutInflater)
}
