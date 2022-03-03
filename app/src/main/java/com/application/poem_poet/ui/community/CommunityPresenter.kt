package com.application.poem_poet.ui.community

import com.application.poem_poet.domain.CommunityUseCase
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import javax.inject.Inject

class CommunityPresenter @Inject constructor(
    private val mainUseCase: CommunityUseCase

) : CommunityActivityPresenter() {

    var array = emptyArray<String>()
    lateinit var name: String
    private var refReceivingName: DatabaseReference? = null
    private var firebaseUser: FirebaseUser? = null

    override fun receivingName() {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        refReceivingName =
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        refReceivingName!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val user: User? = p0.getValue(User::class.java)
                    name = user!!.login
                    receivingPoem()
                }
            }
        })
    }

    override fun getCheckDetailedFragment(): String? {
        return mainUseCase.checkDetailedFragment
    }

    override fun setCheckDetailedFragment(mark: String?) {
        mainUseCase.checkDetailedFragment = mark
    }

    override  fun receivingPoem() {
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
                        if (poem!!.username == name && poem.namePoet == "") {
                            array += poem.id
                        }
                    }
                }
            }
        })
    }
}