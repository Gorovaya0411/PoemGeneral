package com.application.poem_poet.ui.auxiliary_fragment.detailed_poem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private var like: Int = 0
    private var checkActivity: Boolean = false
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
        like = arguments?.getInt("like")!!
        arguments?.getString("id")?.let { detailedPoemPresenter.savingValueCheckBoxAddPoem(it) }
        arguments?.getString("id")?.let { detailedPoemPresenter.savingValueCheckBoxLike(it) }
        detailedPoemPresenter.addEmail()

        when (contextActivity.communityPresenter.getCheckDetailedFragment()) {
            "FromProfile" -> {
                binding.detailedPoemGeneralBtn.setBackgroundResource(R.drawable.bg_detailed_poem_edit_btn)
            }
            "FromList" -> {
                binding.detailedPoemGeneralBtn.setBackgroundResource(R.drawable.bg_detailed_poem_show_more_btn)
            }
        }

        with(binding) {

            val namePoetModified = arguments?.getString("namePoet")?.replace("|", ".", true)
            val usernameModified = arguments?.getString("username")?.replace("|", ".", true)

            if (arguments?.getString("namePoet") == "") {
                detailedPoemNameTxt.text = usernameModified
            } else {
                detailedPoemNameTxt.text = namePoetModified
            }

            Picasso.get()
                .load(arguments?.getString("avatar"))
                .into(detailedPoemImageImg)

            detailedPoemPoemTxt.text = arguments?.getString("poem")
            detailedPoemTitleTxt.text = arguments?.getString("titlePoem")
            firebaseUser = FirebaseAuth.getInstance().currentUser


            detailedPoemGeneralBtn.setOnClickListener {
                when (contextActivity.communityPresenter.getCheckDetailedFragment()) {
                    "FromProfile" -> {
                        openingNewFragment(
                            arguments?.getString("titlePoem")!!,
                            arguments?.getString("namePoet")!!,
                            arguments?.getString("poem")!!,
                            arguments?.getString("avatar")!!,
                            arguments?.getString("genre")!!,
                            arguments?.getString("username")!!,
                            arguments?.getInt("like")!!,
                            arguments?.getString("uid")!!,
                            arguments?.getString("id")!!,
                            R.id.changePoemFragment
                        )
                    }
                    "FromList" -> {
                        openingNewFragment(
                            arguments?.getString("titlePoem")!!,
                            arguments?.getString("namePoet")!!,
                            arguments?.getString("poem")!!,
                            arguments?.getString("avatar")!!,
                            arguments?.getString("genre")!!,
                            arguments?.getString("username")!!,
                            arguments?.getInt("like")!!,
                            arguments?.getString("uid")!!,
                            arguments?.getString("id")!!,
                            R.id.fullInformationFragment
                        )
                    }
                }
            }

            detailedPoemLikeCheckBox.setOnCheckedChangeListener { _, isChecked ->
                detailedPoemPresenter.workCheckboxLike(
                    isChecked,
                    checkActivity,
                    like,
                    arguments?.getString("id")!!,
                    arguments?.getString("namePoet")!!,
                    arguments?.getString("uid")!!
                )
                checkActivity = detailedPoemPresenter.workCheckboxLike(
                    isChecked,
                    checkActivity,
                    like,
                    arguments?.getString("id")!!,
                    arguments?.getString("namePoet")!!,
                    arguments?.getString("uid")!!
                )
            }

            detailedPoemAddPoemCheckBox.setOnCheckedChangeListener { _, isChecked ->
                detailedPoemPresenter.workCheckboxAdd(
                    isChecked,
                    arguments?.getString("id")!!,
                    contextActivity
                )
            }
        }
    }

    override fun addMyAdded() {
        refMyPoem =
            FirebaseDatabase.getInstance().reference.child("Users")
                .child(firebaseUser!!.uid).child("MyAdded")
                .child(arguments?.getString("id")!!)

        val poemHashMap = HashMap<String, Any>()
        poemHashMap["titlePoem"] = arguments?.getString("titlePoem")!!
        poemHashMap["namePoet"] = arguments?.getString("namePoet")!!
        poemHashMap["username"] = arguments?.getString("username")!!
        poemHashMap["genre"] = arguments?.getString("genre")!!
        poemHashMap["poem"] = arguments?.getString("poem")!!
        poemHashMap["id"] = arguments?.getString("id")!!
        poemHashMap["avatar"] = arguments?.getString("avatar")!!
        poemHashMap["like"] = arguments?.getInt("like")!!

        refMyPoem.updateChildren(poemHashMap)

    }

    override fun addFavouritesPoem() {
        refFavouritesPoem =
            FirebaseDatabase.getInstance().reference.child("Users")
                .child(firebaseUser!!.uid).child("MyFavouritesPoem")
                .child(arguments?.getString("id")!!)

        val poemHashMap = HashMap<String, Any>()
        poemHashMap["id"] = arguments?.getString("id")!!

        refFavouritesPoem.updateChildren(poemHashMap)

    }

    override fun workWithCheckbox() {
        binding.detailedPoemAddPoemCheckBox.isChecked = true
    }

    override fun workWithLike() {
        checkActivity = true
        binding.detailedPoemLikeCheckBox.isChecked = true
        like = arguments?.getInt("like")!!
    }

    private fun openingNewFragment(
        titlePoem: String,
        namePoet: String,
        poem: String,
        avatar: String,
        genre: String,
        username: String,
        like: Int,
        uid: String,
        id: String,
        view: Int
    ) {
        val bundle = Bundle()
        with(bundle) {
            putString("titlePoem", titlePoem)
            putString("namePoet", namePoet)
            putString("poem", poem)
            putString("avatar", avatar)
            putString("genre", genre)
            putString("username", username)
            putInt("like", like)
            putString("uid", uid)
            putString("id", id)
        }
        findNavController().navigate(view, bundle)
    }
}
