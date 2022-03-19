package com.application.poem_poet.ui.general_navigation.poets.navigation.poets

import com.application.poem_poet.model.PoemAnswer
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
        val refUser = FirebaseDatabase.getInstance().reference.child("Poem")
        refUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val children = p0.children
                children.forEach {
                    val poem: PoemAnswer? = it.getValue(PoemAnswer::class.java)
                    if (poem != null) {
                        model.communityPresenter.setSaveIdUser(poem.uid)
                    }
                    if (poem!!.namePoet != "") {
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