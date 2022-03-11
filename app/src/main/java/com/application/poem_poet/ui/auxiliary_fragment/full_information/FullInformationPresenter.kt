package com.application.poem_poet.ui.auxiliary_fragment.full_information

import com.application.poem_poet.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import javax.inject.Inject

class FullInformationPresenter @Inject constructor() : FullInformationImpl() {



    private fun getAvatarNew() {
        val refAvatar = FirebaseDatabase.getInstance().reference.child("Poem").child(getModel.id)
        refAvatar.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val avatar: PoemAnswer? = p0.getValue(PoemAnswer::class.java)

                Picasso.get()
                    .load(avatar!!.avatar)
                    .into(imageViewAvatar)

            }
        })
    }

    private fun getData() {

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
                populateData(listPoemPoet)
            }
        })
    }

    private fun addBio() {
        val refBio = FirebaseDatabase.getInstance().reference.child(pathName)
        refBio.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val bio: Bio? = p0.getValue(Bio::class.java)
                    biog = bio!!.biography
                    convertData()
                }
            }
        })

    }

    private fun addInfoForUser() {
        val refInfo = FirebaseDatabase.getInstance().reference.child(getModel.uid)
        refInfo.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val info: User? = p0.getValue(User::class.java)
                    status = info!!.status
                    address = info.address
                    uid = info.uid
                    convertData()
                }
            }
        })

    }

}