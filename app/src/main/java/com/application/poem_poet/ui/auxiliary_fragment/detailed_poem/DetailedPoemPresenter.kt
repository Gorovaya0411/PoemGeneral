package com.application.poem_poet.ui.auxiliary_fragment.detailed_poem

import android.widget.Toast
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.UserGeneral
import com.application.poem_poet.ui.community.CommunityActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class DetailedPoemPresenter @Inject constructor() : DetailedPoemViewImpl() {
    var arrayAddPoem = emptyArray<String>()
    private var firebaseUser: FirebaseUser? = null
    private lateinit var idUser: String
    var arrayLike = emptyArray<String>()


    override fun savingValueCheckBoxAddPoem(model: String) {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        val refReceivingPoem =
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
                .child("MyAdded")
        refReceivingPoem.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val children = p0.children
                    children.forEach {
                        val poem: PoemAnswer? = it.getValue(PoemAnswer::class.java)
                        arrayAddPoem += poem!!.id

                    }
                    arrayAddPoem.forEach {
                        if (model == it) {
                            viewState.workWithCheckbox()
                        }
                    }
                }
            }
        })
    }

    override fun addEmail() {
        val refReceivingPoem =
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        refReceivingPoem.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {

                    val email: UserGeneral? = p0.getValue(UserGeneral::class.java)
                    idUser = email!!.uid
                }
            }
        })
    }

    override fun savingValueCheckBoxLike(model: String) {
        val refReceivingPoem =
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
                .child("MyFavouritesPoem")
        refReceivingPoem.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val children = p0.children
                    children.forEach {
                        val poem: PoemAnswer? = it.getValue(PoemAnswer::class.java)
                        arrayLike += poem!!.id

                    }
                    arrayLike.forEach {
                        if (model == it) {
                            viewState.workWithLike()
                        }
                    }
                }
            }
        })
    }

    override fun workCheckboxLike(
        isChecked: Boolean,
        checkActivity: Boolean,
        like: Int,
        id: String,
        namePoet: String,
        uid: String
    ): Boolean {
        var likeAdapt = like
        var check = checkActivity
        if (isChecked) {
            if (!checkActivity) {
                viewState.addFavouritesPoem()
                likeAdapt++
                val refLike =
                    FirebaseDatabase.getInstance().reference.child("Poem")
                        .child(id).child("like")
                refLike.setValue(likeAdapt)
                val refLikeForMyJob =
                    FirebaseDatabase.getInstance().reference.child("Users")
                        .child(uid).child("MyJob")
                        .child(id).child("like")
                refLikeForMyJob.setValue(likeAdapt)

                if (namePoet != "") {
                    val refLikeForNamePoet =
                        FirebaseDatabase.getInstance().reference.child(namePoet)
                            .child("Poems").child(id).child("like")
                    refLikeForNamePoet.setValue(likeAdapt)
                } else {
                    val refLikeForUserName =
                        FirebaseDatabase.getInstance().reference.child(idUser)
                            .child("Poems").child(id).child("like")
                    refLikeForUserName.setValue(likeAdapt)
                }
            }
        } else {
            likeAdapt--
            check = false
            val refLike =
                FirebaseDatabase.getInstance().reference.child("Poem")
                    .child(id).child("like")
            refLike.setValue(likeAdapt)
            val refDeleteFavouritesPoem =
                FirebaseDatabase.getInstance().reference.child("Users")
                    .child(firebaseUser!!.uid).child("MyFavouritesPoem")
                    .child(id)
            refDeleteFavouritesPoem.removeValue()
            val refLikeForMyJob =
                FirebaseDatabase.getInstance().reference.child("Users")
                    .child(uid).child("MyJob")
                    .child(id).child("like")
            refLikeForMyJob.setValue(likeAdapt)
            if (namePoet != "") {
                val refLikeForNamePoet =
                    FirebaseDatabase.getInstance().reference.child(namePoet)
                        .child("Poems").child(id).child("like")
                refLikeForNamePoet.setValue(likeAdapt)
            } else {
                val refLikeForUserName =
                    FirebaseDatabase.getInstance().reference.child(idUser)
                        .child("Poems").child(id).child("like")
                refLikeForUserName.setValue(likeAdapt)
            }
        }
        return check
    }

    override fun workCheckboxAdd(isChecked: Boolean,id: String, con:CommunityActivity) {
        if (isChecked) {
            viewState.addMyAdded()
        } else {
            val refDeletePoem =
                FirebaseDatabase.getInstance().reference.child("Users")
                    .child(firebaseUser!!.uid)
                    .child("MyAdded").child(id)
            refDeletePoem.removeValue()
            Toast.makeText(
                con,
                "Удалено из добавленных:)",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}