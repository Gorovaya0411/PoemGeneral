package com.application.poem_poet.ui.auxiliary_fragment.detailed_poem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentDetailedPoemBinding
import com.application.poem_poet.ui.community.CommunityActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class DetailedPoemFragment : MvpAppCompatFragment(), DetailedPoemView {

    @InjectPresenter
    lateinit var detailedPoemPresenter: DetailedPoemPresenter
    private var firebaseUser: FirebaseUser? = null
    private val contextActivity: CommunityActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as CommunityActivity)
    }
    private var likeHere: Int = 0
    lateinit var binding: FragmentDetailedPoemBinding
    private lateinit var refFavouritesPoem: DatabaseReference
    private lateinit var refMyPoem: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_detailed_poem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailedPoemBinding.bind(view)
        workBackStack()

        with(contextActivity.communityPresenter.getSavePoemHelp()) {
            likeHere = like
            id.let { detailedPoemPresenter.savingValueCheckBoxAddPoem(it) }
            id.let { detailedPoemPresenter.savingValueCheckBoxLike(it) }

            with(binding) {
                val namePoetModified = namePoet.replace("|", ".", true)
                val usernameModified = username.replace("|", ".", true)

                detailedPoemPoemTxt.text = poem
                detailedPoemTitleTxt.text = titlePoem
                firebaseUser = FirebaseAuth.getInstance().currentUser
                Picasso.get()
                    .load(avatar)
                    .into(detailedPoemImageImg)

                when (contextActivity.communityPresenter.getCheckDetailedFragment()) {
                    "FromProfile" -> detailedPoemGeneralBtn.setBackgroundResource(R.drawable.bg_detailed_poem_edit_btn)
                    "FromList" -> detailedPoemGeneralBtn.setBackgroundResource(R.drawable.bg_detailed_poem_show_more_btn)
                    "FromMyPoem" -> {
                        detailedPoemLikeCheckBox.visibility = CheckBox.GONE
                        detailedPoemAddPoemCheckBox.visibility = CheckBox.GONE
                    }
                }

                if (namePoet == "") {
                    detailedPoemNameTxt.text = usernameModified
                } else {
                    detailedPoemNameTxt.text = namePoetModified
                }

                detailedPoemGeneralBtn.setOnClickListener {
                    when (contextActivity.communityPresenter.getCheckDetailedFragment()) {
                        "FromProfile" -> findNavController().navigate(R.id.changePoemFragment)
                        "FromList" -> findNavController().navigate(R.id.fullInformationFragment)
                        "FromMyPoem" -> findNavController().navigate(R.id.fullInformationFragment)
                    }
                }

                detailedPoemBackImg.setOnClickListener {
                    when (contextActivity.communityPresenter.getCheckDetailedFragment()) {
                        "FromProfile" -> contextActivity.backDetailToGeneralFragmentProfile()
                        "FromList" -> contextActivity.backDetailToGeneralFragmentList()
                        "FromMyPoem" -> contextActivity.backDetailToGeneralFragmentMyPoem()
                    }
                }

                detailedPoemLikeCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    detailedPoemPresenter.workCheckboxLike(
                        isChecked,
                        likeHere,
                        id,
                        namePoet,
                        uid,
                        contextActivity.communityPresenter.getSaveUserGeneral().uid
                    )
                    detailedPoemPresenter.workCheckboxLike(
                        isChecked,
                        likeHere,
                        id,
                        namePoet,
                        uid,
                        contextActivity.communityPresenter.getSaveUserGeneral().uid
                    )
                }

                detailedPoemAddPoemCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    detailedPoemPresenter.workCheckboxAdd(
                        isChecked,
                        uid,
                        contextActivity
                    )
                }
            }
        }
    }

    private fun workBackStack() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    when (contextActivity.communityPresenter.getCheckDetailedFragment()) {
                        "FromProfile" -> contextActivity.backDetailToGeneralFragmentProfile()
                        "FromList" -> contextActivity.backDetailToGeneralFragmentList()
                        "FromMyPoem" -> contextActivity.backDetailToGeneralFragmentMyPoem()
                    }
                }
            }
            )
    }

    override fun addMyAdded() {
        with(contextActivity.communityPresenter.getSavePoemHelp()) {
            refMyPoem =
                FirebaseDatabase.getInstance().reference.child("Users")
                    .child(firebaseUser!!.uid).child("MyAdded")
                    .child(id)

            val poemHashMap = HashMap<String, Any>()
            poemHashMap["titlePoem"] = titlePoem
            poemHashMap["namePoet"] = namePoet
            poemHashMap["username"] = username
            poemHashMap["genre"] = genre
            poemHashMap["poem"] = poem
            poemHashMap["id"] = id
            poemHashMap["avatar"] = avatar
            poemHashMap["like"] = like

            refMyPoem.updateChildren(poemHashMap)
        }
    }

    override fun addFavouritesPoem() {
        refFavouritesPoem =
            FirebaseDatabase.getInstance().reference.child("Users")
                .child(firebaseUser!!.uid).child("MyFavouritesPoem")
                .child(contextActivity.communityPresenter.getSavePoemHelp().id)

        val poemHashMap = HashMap<String, Any>()
        poemHashMap["id"] = contextActivity.communityPresenter.getSavePoemHelp().id

        refFavouritesPoem.updateChildren(poemHashMap)
    }

    override fun workWithCheckbox() {
        binding.detailedPoemAddPoemCheckBox.isChecked = true
    }

    override fun workWithLike() {
        binding.detailedPoemLikeCheckBox.isChecked = true
        likeHere = contextActivity.communityPresenter.getSavePoemHelp().like
    }
}
