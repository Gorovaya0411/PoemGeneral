package com.application.poem_poet.ui.general_navigation.poets.navigation.poets

import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.UserGeneral
import com.application.poem_poet.model.UserGeneralSave
import com.application.poem_poet.ui.community.CommunityActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class ListPoetsPresenter @Inject constructor() : ListPoetPresenterImpl() {
    private val myAdapter =
        AdapterListPoets { openingNewActivity(it) }
    private var listPoemPoet: MutableList<PoemAnswer?> = mutableListOf()
    private var listPoemPoetRand: List<PoemAnswer?> = mutableListOf()
    private var firebaseUser: FirebaseUser? = null

    override fun getData(model: CommunityActivity) {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        viewState.workWithAdapter(myAdapter)
        viewState.workWithSearchWidget(myAdapter)
        val refPoem = FirebaseDatabase.getInstance().reference.child("Poem")
        refPoem.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val children = p0.children
                children.forEach {
                    val poem: PoemAnswer? = it.getValue(PoemAnswer::class.java)
                    if (poem!!.namePoet != "") {
                        listPoemPoet.add(poem)
                        listPoemPoetRand = listPoemPoet.shuffled()
                    }
                }
                populateData(listPoemPoetRand as MutableList<PoemAnswer?>)
            }
        })

        val refUser =
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        refUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val userGeneral: UserGeneral? = p0.getValue(UserGeneral::class.java)
                if (userGeneral != null) {

                    model.communityPresenter.setSaveUserGeneral(
                        UserGeneralSave(
                            userGeneral.email,
                            userGeneral.login,
                            userGeneral.avatar,
                            userGeneral.status,
                            userGeneral.address,
                            userGeneral.uid
                        )
                    )
                }
            }
        })
    }

    override fun populateData(poems: MutableList<PoemAnswer?>) {
        myAdapter.setData(poems)
    }

    override fun openingNewActivity(model: PoemAnswer) {
        viewState.openingNewActivity(model)
    }
}