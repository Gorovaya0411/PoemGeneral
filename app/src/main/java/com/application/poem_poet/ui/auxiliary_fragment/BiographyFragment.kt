package com.application.poem_poet.ui.auxiliary_fragment


import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.navigation.fragment.findNavController
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentBiographyBinding
import com.application.poem_poet.ui.base.BaseFragment
import com.application.poem_poet.ui.main.MainActivity

class BiographyFragment : BaseFragment<FragmentBiographyBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.biographyBackImg.setOnClickListener {
            val bundle = Bundle()
            with(bundle) {
                putString("username", arguments?.getString("username")!!)
                putString("titlePoem", arguments?.getString("titlePoem")!!)
                putString("namePoet", arguments?.getString("namePoet")!!)
                putString("poem", arguments?.getString("poem")!!)
                putString("avatar", arguments?.getString("avatar")!!)
                putInt("like", arguments?.getInt("like")!!)
                putString("id", arguments?.getString("id")!!)
                putString("uid", arguments?.getString("uid")!!)
                putString("genre", arguments?.getString("genre")!!)
            }
            findNavController().navigate(R.id.fullInformationFragment, bundle)
        }

        binding.biographyBioTxt.text = arguments?.getString("bio")!!
    }

    override fun initViewBinding() = FragmentBiographyBinding.inflate(layoutInflater)
}
