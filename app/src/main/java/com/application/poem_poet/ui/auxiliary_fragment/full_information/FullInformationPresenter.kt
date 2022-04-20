package com.application.poem_poet.ui.auxiliary_fragment.full_information

import com.application.poem_poet.model.Bio
import com.application.poem_poet.model.PoemAnswer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class FullInformationPresenter @Inject constructor() : FullInformationImpl() {
    private var listPoemPoet: MutableList<PoemAnswer?> = mutableListOf()
    var biog = ""

    override fun getData(pathName: String) {
        val refPoet = FirebaseDatabase.getInstance().reference.child(pathName).child("Poems")
        refPoet.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val children = p0.children
                children.forEach {
                    val poem: PoemAnswer? = it.getValue(PoemAnswer::class.java)
                    listPoemPoet.add(poem)
                }
                viewState.populateData(listPoemPoet)
            }
        })
    }

    override fun addBio(pathName: String): String {
        val refBio = FirebaseDatabase.getInstance().reference.child(pathName)
        refBio.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val bio: Bio? = p0.getValue(Bio::class.java)
                    biog = bio!!.biography
                    viewState.convertData(biog)
                }
            }
        })
        return biog
    }
}