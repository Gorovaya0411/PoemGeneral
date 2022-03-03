package com.application.poem_poet.ui.general_navigation.poets.navigation.users

import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.ui.general_navigation.profile.ProfilePresenterImpl
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import moxy.MvpPresenter
import javax.inject.Inject

class ListUsersPresenter @Inject constructor() : ListUserPresenterImpl() {
    private var listPoemPoet: MutableList<PoemAnswer?> = mutableListOf()
    private var listPoemPoetRand: List<PoemAnswer?> = mutableListOf()
    private val myAdapter =
        AdapterListUser { openingNewActivity(it) }

    override fun getData() {
        viewState.workWithAdapter(myAdapter)
        viewState.workWithSearchWidget(myAdapter)
        val refUser = FirebaseDatabase.getInstance().reference.child("Poem")
        refUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val children = p0.children
                children.forEach {

                    val poem: PoemAnswer? = it.getValue(PoemAnswer::class.java)
                    if (poem!!.namePoet == "") {
                        listPoemPoet.add(poem)
                        listPoemPoetRand = listPoemPoet.shuffled()
                    }


                }
                populateData(listPoemPoetRand as MutableList<PoemAnswer?>)
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