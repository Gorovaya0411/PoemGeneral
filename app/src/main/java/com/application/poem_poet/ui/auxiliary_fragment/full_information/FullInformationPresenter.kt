package com.application.poem_poet.ui.auxiliary_fragment.full_information

import android.util.Log
import android.widget.ImageView
import com.application.poem_poet.model.Bio
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.User
import com.application.poem_poet.model.WorkAddInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import javax.inject.Inject


class FullInformationPresenter @Inject constructor() : FullInformationImpl() {
    private var listPoemPoet: MutableList<PoemAnswer?> = mutableListOf()
    var biog = ""

    override fun getAvatarNew(id: String, view: ImageView) {
        val refAvatar = FirebaseDatabase.getInstance().reference.child("Poem").child(id)
        refAvatar.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val avatar: PoemAnswer? = p0.getValue(PoemAnswer::class.java)

                Picasso.get()
                    .load(avatar!!.avatar)
                    .into(view)
            }
        })
    }

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
                Log.e("sdfsfdsf", p0.message)
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

    override fun addInfoForUser(uidGet: String): WorkAddInfo {
        var status = ""
        var address = ""
        var uid = ""
        val refInfo = FirebaseDatabase.getInstance().reference.child(uidGet)
        refInfo.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val info: User? = p0.getValue(User::class.java)
                    status = info!!.status
                    address = info.address
                    uid = info.uid
                    viewState.convertData(biog)
                }
            }
        })
        return WorkAddInfo(status, address, uid)
    }
}