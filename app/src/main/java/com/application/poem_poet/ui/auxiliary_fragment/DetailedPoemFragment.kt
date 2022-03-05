package com.application.poem_poet.ui.auxiliary_fragment

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import com.application.poem_poet.databinding.FragmentDetailedPoemBinding
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.ui.base.BaseFragment
import com.application.poem_poet.ui.community.CommunityActivity
import com.application.poem_poet.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class DetailedPoemFragment : BaseFragment<FragmentDetailedPoemBinding>() {
    private var firebaseUser: FirebaseUser? = null
    private val contextActivity: CommunityActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as CommunityActivity)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getModelPoemAnswer = contextActivity.intent.getSerializableExtra("KEY") as PoemAnswer
        val namePoetModified = getModelPoemAnswer.namePoet.replace("|", ".", true)
        val usernameModified = getModelPoemAnswer.username.replace("|", ".", true)

        textViewGenre.text = getModelPoemAnswer.genre
        textViewTitlePoem.movementMethod = ScrollingMovementMethod()
        textViewPoem.text = getModelPoemAnswer.poem
        textViewTitlePoem.text = getModelPoemAnswer.titlePoem
        textViewTitlePoem.setOnClickListener { textViewTitlePoem.maxLines = Integer.MAX_VALUE }
        firebaseUser = FirebaseAuth.getInstance().currentUser

        workWithToolbar()

        imageButton.setOnClickListener {
            openingChangeActivity(getModelPoemAnswer)
        }

        if (getModelPoemAnswer.namePoet == "") {
            buttonNamePoet.text = usernameModified
            buttonNamePoet.setOnClickListener {
                openingNewActivity(getModelPoemAnswer)
            }
        } else {
            buttonNamePoet.text = namePoetModified
            buttonNamePoet.setOnClickListener {
                openingNewActivity(getModelPoemAnswer)
            }
        }
    }

    private fun workWithToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = " "
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun openingNewActivity(model: PoemAnswer) {
        val intent = Intent(this, DetailedPoetAndUserActivity::class.java)
        intent.putExtra("KEY", model)
        startActivity(intent)
    }

    private fun openingChangeActivity(model: PoemAnswer) {
        val intent = Intent(this, ChangeYourPoemActivity::class.java)
        intent.putExtra("KEY", model)
        startActivity(intent)
    }

    override fun initViewBinding() = FragmentDetailedPoemBinding.inflate(layoutInflater)
}
