package com.application.poem_poet.ui.auxiliary_fragment.full_information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentFullInformationBinding
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.ui.community.CommunityActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter


class FullInformationFragment : MvpAppCompatFragment(), FullInformationView {

    private lateinit var pathName: String
    private var addressHere = ""
    private var statusHere = ""
    lateinit var binding: FragmentFullInformationBinding
    private var uidHere = ""
    private var firebaseUser: FirebaseUser? = null
    private val myAdapter =
        AdapterFullInformation { openingNewActivity() }
    private val contextActivity: CommunityActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as CommunityActivity)
    }
    var bio = ""

    @InjectPresenter
    lateinit var fullInformationPresenter: FullInformationPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_full_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFullInformationBinding.bind(view)
        binding.fullInformationAddInfo.visibility = ImageView.INVISIBLE
        with(binding) {

            val namePoetChanged = arguments?.getString("namePoet")!!.replace("|", ".", true)
            val nameUsernameChanged = arguments?.getString("username")!!.replace("|", ".", true)

            Picasso.get()
                .load(arguments?.getString("avatar")!!)
                .into(listUsersAvatarUsersImg)
            firebaseUser = FirebaseAuth.getInstance().currentUser


            if (arguments?.getString("namePoet")!! == "") {
                fullInformationTitleTxt.text = nameUsernameChanged
                pathName = arguments?.getString("uid")!!
                with(fullInformationPresenter.addInfoForUser(arguments?.getString("uid")!!)) {
                    addressHere = address
                    statusHere = status
                    uidHere = uid
                }
            } else {
                fullInformationPresenter.getAvatarNew(
                    arguments?.getString("id")!!,
                    binding.listUsersAvatarUsersImg
                )
                binding.fullInformationTitleTxt.text = namePoetChanged
                pathName = arguments?.getString("namePoet")!!
                fullInformationPresenter.addBio(pathName)
            }

            workWithAdapter()
            fullInformationPresenter.getData(pathName)

            binding.fullInformationAddInfo.setOnClickListener {
                binding.fullInformationAddInfo.setBackgroundResource(R.drawable.ic_full_information_add_info_purple)
                openAddInfo()
            }

            binding.fullInformationBackImg.setOnClickListener {
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
                findNavController().navigate(R.id.detailedPoemFragment, bundle)
            }

            binding.fullInformationBioTxt.setOnClickListener {
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
                    putString("bio", bio)
                }
                findNavController().navigate(R.id.biographyFragment, bundle)
            }
        }
    }

    private fun workWithAdapter() {
        binding.fullInformationRecyclerView.layoutManager =
            LinearLayoutManager(contextActivity, RecyclerView.VERTICAL, false)
        binding.fullInformationRecyclerView.adapter = myAdapter
    }

    override fun populateData(listPoemPoet: MutableList<PoemAnswer?>) {
        myAdapter.setData(listPoemPoet)
    }

    private fun openingNewActivity() {
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
        findNavController().navigate(R.id.detailedPoemFragment, bundle)
    }

    private fun openAddInfo() {
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
        findNavController().navigate(R.id.addAdditionalInfoFragment, bundle)
    }

    override fun convertData(biog: String) {
        bio = biog
        if (arguments?.getString("namePoet")!! == "") {
            binding.fullInformationCommunicationUserTxt.text = addressHere
            binding.fullInformationBioTxt.text = statusHere
            if (statusHere == "") {
                binding.fullInformationBioTxt.text = "..."
            }
        } else {
            if (biog != "") {
                binding.fullInformationBioTxt.text = "Биография ->"
            } else {
                binding.fullInformationBioTxt.text = "..."
            }
            if (biog == "" || arguments?.getString("avatar")!! == "https://firebasestorage.googleapis.com/v0/b/poemspoets-130cd.appspot.com/o/icon.png?alt=media&token=5935d9cc-88cf-4697-8ed3-17abd66e9fee") {
                binding.fullInformationAddInfo.visibility = ImageView.VISIBLE
            }
        }
    }
}
