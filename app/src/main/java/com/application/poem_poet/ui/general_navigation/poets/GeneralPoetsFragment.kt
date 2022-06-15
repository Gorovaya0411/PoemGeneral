package com.application.poem_poet.ui.general_navigation.poets

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentGeneralPoetsBinding
import com.application.poem_poet.ui.base.BaseFragment
import com.application.poem_poet.ui.community.CommunityActivity

class GeneralPoetsFragment : BaseFragment<FragmentGeneralPoetsBinding>() {

    private val contextActivity: CommunityActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as CommunityActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.generalPoetsFloatingActionBar.setOnClickListener {
            findNavController().navigate(R.id.action_generalPoetsFragment_to_addPoemFragment)
        }

        binding.generalPoetsFloatingActionBar.isEnabled = contextActivity.lackInternet()
    }

    override fun onStart() {
        super.onStart()
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
