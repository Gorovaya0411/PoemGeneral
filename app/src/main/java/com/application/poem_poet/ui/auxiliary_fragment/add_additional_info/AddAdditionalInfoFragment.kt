package com.application.poem_poet.ui.auxiliary_fragment.add_additional_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
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
        with(binding) {
            refStorageRoot = FirebaseStorage.getInstance().reference
            addAdditionalInfoProgressBar.visibility = ProgressBar.INVISIBLE

            addAdditionalInfoPresenter.getAvatar(arguments?.getString("id")!!)
            addAdditionalInfoPresenter.getBio(
                arguments?.getString("namePoet")!!,
                addAdditionalInfoBioEditTxt
            )

            addAdditionalInfoAddBtn.setOnClickListener {
                addAdditionalInfoPresenter.addBio(
                    arguments?.getString("namePoet")!!,
                    contextActivity,
                    addAdditionalInfoBioEditTxt
                )
            }

            addAdditionalInfoBackImg.setOnClickListener {
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

            addAdditionalInfoPhotoCardView.setOnClickListener {
                contextActivity.communityPresenter.setCheckCropFragment("add")
                addAdditionalInfoPresenter.changePhotoUser(contextActivity)
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

    override fun broadcastData() {
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
}






