package com.application.poem_poet.ui.auxiliary_fragment.full_information

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentFullInformationBinding
import com.application.poem_poet.dialogFragments.ForEmptyInfoDialog
import com.application.poem_poet.model.PoemAnswer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter


class FullInformationFragment : MvpAppCompatFragment(), FullInformationView {

    private lateinit var pathName: String
    private val emptyMyPoemDialog = ForEmptyInfoDialog(::openAddInfo)
    private var biog = ""
    private var address = ""
    private var status = ""
    lateinit var binding: FragmentFullInformationBinding
    private var uid = ""
    private var firebaseUser: FirebaseUser? = null
    private val myAdapter =
        AdapterFullInformation { openingNewActivity(it) }

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
        with(binding) {

            val namePoetChanged = arguments?.getString("namePoet")!!.replace("|", ".", true)
            val nameUsernameChanged = arguments?.getString("username")!!.replace("|", ".", true)

            Picasso.get()
                .load(arguments?.getString("avatar")!!)
                .into(listUsersAvatarUsersImg)
            firebaseUser = FirebaseAuth.getInstance().currentUser


            if (arguments?.getString("namePoet")!! == "") {
                cardViewBio.visibility = CardView.INVISIBLE
                fullInformationTitleTxt.text = nameUsernameChanged
                pathName = arguments?.getString("uid")!!
                addInfoForUser()
            } else {
                getAvatarNew()
                textViewYourCommunication.visibility = TextView.INVISIBLE
                textViewUsernameOrNamePoet.text = namePoetChanged
                pathName = getModel.namePoet
                addBio()
            }

            workWithAdapter()
            getData()

            imageViewAdd.setOnClickListener {
                openAddInfo()
            }

            buttonBiography.setOnClickListener {
                val intent = Intent(this, BiographyActivity::class.java)
                intent.putExtra("KEY", biog)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun workWithAdapter() {
        RecyclerViewPoetAndUser.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        RecyclerViewPoetAndUser.adapter = myAdapter
    }

    override fun populateData(listPoemPoet: MutableList<PoemAnswer?>) {
        myAdapter.setData(listPoemPoet)
    }

    private fun openingNewActivity(model: PoemAnswer) {
        val intent = Intent(this, DetailedPoemGeneralActivity::class.java)
        intent.putExtra("KEY", model)
        startActivity(intent)
        finish()
    }

    private fun openAddInfo() {
        val intent = Intent(this, AddAdditionalInfoActivity::class.java)
        intent.putExtra("KEY", getModel)
        startActivity(intent)
        finish()
    }

    private fun convertData() {
        if (getModel.namePoet == "") {
            textViewInfoAvailable.visibility = TextView.INVISIBLE
            imageViewAvatar.visibility = CardView.INVISIBLE
            if (status != "") {
                textViewStatus.text = status
            } else {
                textViewStatus.text = "Статус отсутствует"
            }
            if (address != "") {
                textViewCommunication.text = address
            } else {
                textViewCommunication.visibility = TextView.INVISIBLE
                textViewYourCommunication.visibility = TextView.INVISIBLE
            }

        } else {

            if (biog == "" && getModel.avatar == "https://firebasestorage.googleapis.com/v0/b/poemspoets-9db16.appspot.com/o/icon.png?alt=media&token=3b87c7de-0a6f-48ab-8933-a72637c290ac") {
                cardViewBio.visibility = CardView.INVISIBLE
                textViewAddInfo.visibility = TextView.VISIBLE
                imageViewAdd.visibility = ImageView.VISIBLE
                emptyMyPoemDialog.show(this.supportFragmentManager, "ForDeleteMyPoemDialog")
                textViewAddInfo.visibility = TextView.VISIBLE
                imageViewAdd.visibility = ImageView.VISIBLE
            } else if (getModel.avatar != "https://firebasestorage.googleapis.com/v0/b/poemspoets-9db16.appspot.com/o/icon.png?alt=media&token=3b87c7de-0a6f-48ab-8933-a72637c290ac" && biog == "") {
                textViewAddInfo.visibility = TextView.VISIBLE
                cardViewBio.visibility = CardView.INVISIBLE
                textViewInfoAvailable.text = "Биография отсуствует"
                imageViewAdd.visibility = ImageView.VISIBLE
            } else if (biog != "" && getModel.avatar == "https://firebasestorage.googleapis.com/v0/b/poemspoets-9db16.appspot.com/o/icon.png?alt=media&token=3b87c7de-0a6f-48ab-8933-a72637c290ac") {
                cardViewBio.visibility = CardView.VISIBLE
                textViewAddInfo.visibility = TextView.VISIBLE
                imageViewAdd.visibility = ImageView.VISIBLE
                textViewInfoAvailable.visibility = TextView.INVISIBLE
            } else {
                textViewInfoAvailable.visibility = TextView.INVISIBLE
                textViewAddInfo.visibility = TextView.INVISIBLE
                imageViewAdd.visibility = ImageView.INVISIBLE
                textViewAddInfo.visibility = TextView.INVISIBLE
                imageViewAdd.visibility = ImageView.INVISIBLE
            }
            textViewStatus.visibility = TextView.INVISIBLE
            imageViewQuotationMarksUp.visibility = ImageView.INVISIBLE
            imageViewQuotationMarksDown.visibility = ImageView.INVISIBLE
        }
    }

}
