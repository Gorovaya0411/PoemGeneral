package com.application.poem_poet.ui.auxiliary_fragment.change_poem

import android.widget.Toast
import com.application.poem_poet.model.Bio
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.PoemHelp
import com.application.poem_poet.ui.community.CommunityActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import javax.inject.Inject

class ChangePoemPresenter @Inject constructor() : ChangeViewImpl() {

    private var firebaseUserID: String = ""
    private lateinit var refPoemPoetOrUser: DatabaseReference
    var array: MutableList<String> = mutableListOf()
    private lateinit var refAllPoem: DatabaseReference
    private lateinit var refJob: DatabaseReference
    private lateinit var getAvatar: String
    private lateinit var mAuth: FirebaseAuth

    override fun changePoem(
        id: String,
        model: CommunityActivity,
        poemHelp: PoemHelp,
        like: Int
    ) {
        mAuth = FirebaseAuth.getInstance()
        firebaseUserID = mAuth.currentUser!!.uid

        val namePoetModified = poemHelp.namePoet.replace(".", "|", true)

        refAllPoem =
            FirebaseDatabase.getInstance().reference.child("Poem").child(id)
        refJob = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserID)
            .child("MyJob").child(id)

        refPoemPoetOrUser = if (namePoetModified == "") {
            FirebaseDatabase.getInstance().reference.child(firebaseUserID).child("Poems")
                .child(id)

        } else {
            FirebaseDatabase.getInstance().reference.child(namePoetModified).child("Poems")
                .child(id)
        }
        if (poemHelp.namePoet != poemHelp.namePoet) {
            FirebaseDatabase.getInstance().reference.child(poemHelp.namePoet)
                .child("Poems").child(id).removeValue()
            addAvatar(poemHelp.namePoet)
        }
        val poemHashMap = HashMap<String, Any>()
        poemHashMap["titlePoem"] = poemHelp.titlePoem
        poemHashMap["namePoet"] = namePoetModified
        poemHashMap["username"] = poemHelp.username
        poemHashMap["genre"] = poemHelp.genre
        poemHashMap["poem"] = poemHelp.poem
        poemHashMap["id"] = id
        poemHashMap["uid"] = poemHelp.uid
        poemHashMap["like"] = like

        refAllPoem.updateChildren(poemHashMap)
        refJob.updateChildren(poemHashMap)
        refPoemPoetOrUser.updateChildren(poemHashMap)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        model,
                        "Ваш стих изменен!:)",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    viewState.back()
                } else {
                    Toast.makeText(
                        model,
                        "Ошибка:" + it.exception!!.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    override fun receivingNamePoet() {
        val refReceivingPoem =
            FirebaseDatabase.getInstance().reference.child("Poem")
        refReceivingPoem.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val children = p0.children
                    children.forEach {
                        val poem: PoemAnswer? = it.getValue(PoemAnswer::class.java)
                        if (poem!!.namePoet != "") {
                            val namePoetModified = poem.namePoet.replace("|", ".", true)
                            array.add(namePoetModified)
                            val set: Set<String> = HashSet(array)
                            array.clear()
                            array.addAll(set)
                        }
                        viewState.workWithAutoTxt(array)
                    }
                }
            }
        })
    }

    override fun addAvatar(namePoet: String) {
        val namePoetModified = namePoet.replace(".", "|", true)
        val refUser =
            FirebaseDatabase.getInstance().reference.child(namePoetModified)
        refUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val avatar: Bio? = p0.getValue(Bio::class.java)
                    getAvatar = avatar!!.avatar
                }
                val poemHashMap = HashMap<String, Any>()
                if (getAvatar == "") {
                    poemHashMap["avatar"] =
                        "https://firebasestorage.googleapis.com/v0/b/poemspoets-9db16.appspot.com/o/icon.png?alt=media&token=3b87c7de-0a6f-48ab-8933-a72637c290ac"
                } else {
                    poemHashMap["avatar"] =
                        getAvatar
                }

                refJob.updateChildren(poemHashMap)
                refAllPoem.updateChildren(poemHashMap)
                refPoemPoetOrUser.updateChildren(poemHashMap)
            }
        })
    }
}