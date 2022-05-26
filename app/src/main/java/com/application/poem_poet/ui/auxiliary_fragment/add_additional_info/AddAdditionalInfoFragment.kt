package com.application.poem_poet.ui.auxiliary_fragment.add_additional_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentAddAdditionalInfoBinding
import com.application.poem_poet.ui.community.CommunityActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class AddAdditionalInfoFragment : MvpAppCompatFragment(), AddAdditionalInfoView {

    private lateinit var refStorageRoot: StorageReference
    private val contextActivity: CommunityActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as CommunityActivity)
    }
    lateinit var binding: FragmentAddAdditionalInfoBinding

    @InjectPresenter
    lateinit var addAdditionalInfoPresenter: AddAdditionalInfoPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_add_additional_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddAdditionalInfoBinding.bind(view)
        workBackStack()

        with(binding) {
            with(contextActivity.communityPresenter.getSavePoemHelp()) {
                refStorageRoot = FirebaseStorage.getInstance().reference
                addAdditionalInfoProgressBar.visibility = ProgressBar.INVISIBLE

                addAdditionalInfoPresenter.getAvatar(id)
                addAdditionalInfoPresenter.getBio(
                    namePoet,
                    addAdditionalInfoBioEditTxt
                )

                addAdditionalInfoAddBtn.setOnClickListener {
                    addAdditionalInfoPresenter.addBio(
                        namePoet,
                        contextActivity,
                        addAdditionalInfoBioEditTxt
                    )
                }

                addAdditionalInfoBackImg.setOnClickListener {
                    findNavController().navigate(R.id.fullInformationFragment)
                }

                addAdditionalInfoPhotoCardView.setOnClickListener {
                    contextActivity.communityPresenter.setCheckCropFragment("add")
                    addAdditionalInfoPresenter.changePhotoUser(contextActivity)
                }
            }
        }
    }

    override fun workDataChange(receivedAvatar: String) {
        Picasso.get()
            .load(receivedAvatar)
            .into(binding.addAdditionalInfoPhotoImg)
        binding.addAdditionalInfoChangePhotoTxt.visibility = TextView.INVISIBLE
        binding.addAdditionalInfoPhotoCardView.isEnabled = false
    }

    private fun workBackStack() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.fullInformationFragment)
                }
            }
            )
    }

    override fun broadcastData() {
        findNavController().navigate(R.id.fullInformationFragment)
    }
}






