package com.application.poem_poet.ui.auxiliary_fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.findNavController
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentBiographyBinding
import com.application.poem_poet.ui.base.BaseFragment

class BiographyFragment : BaseFragment<FragmentBiographyBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.biographyBackImg.setOnClickListener {
            findNavController().navigate(R.id.fullInformationFragment)
        }

//       binding.scrollView.setOnScrollChangeListener(
//            NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
//                binding.appBar.elevate(scrollY != 0)
//            }
//        )

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.fullInformationFragment)
                }
            }
            )

        binding.biographyBioTxt.text = arguments?.getString("bio")!!
    }


    override fun initViewBinding() = FragmentBiographyBinding.inflate(layoutInflater)
}
