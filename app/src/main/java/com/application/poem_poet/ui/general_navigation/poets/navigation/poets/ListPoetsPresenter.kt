package com.application.poem_poet.ui.general_navigation.poets.navigation.poets

import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.PoemHelp
import com.application.poem_poet.model.User
import com.application.poem_poet.model.UserGeneralSave
import com.application.poem_poet.ui.community.CommunityActivity
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

    override fun getData(model: CommunityActivity) {
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
                    model.communityPresenter.setSavePoemHelp(
                        PoemHelp(
                            poem.username,
                            poem.titlePoem,
                            poem.namePoet,
                            poem.genre,
                            poem.poem,
                            poem.avatar,
                            poem.id,
                            poem.uid,
                            poem.like
                        )
                    )
                }
                populateData(listPoemPoetRand as MutableList<PoemAnswer?>)
            }
        })

        val refUser =
            FirebaseDatabase.getInstance().reference.child("Users")
                .child(model.communityPresenter.getSaveUserGeneral().uid)
        refUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val userGeneral: User? = p0.getValue(User::class.java)
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