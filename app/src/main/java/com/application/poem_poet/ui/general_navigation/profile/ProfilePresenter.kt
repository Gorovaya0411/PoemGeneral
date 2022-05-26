package com.application.poem_poet.ui.general_navigation.profile

import com.application.poem_poet.dialogFragments.ForEmptyJobsDialog
import com.application.poem_poet.model.PoemAnswer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import javax.inject.Inject

class ProfilePresenter @Inject constructor() : ProfilePresenterImpl() {
    private var firebaseUser: FirebaseUser? = null
    var array = emptyArray<String>()
    var arrayCatalog = emptyArray<String>()
    var arrayPoem = emptyArray<String>()
    private val emptyMyJobsDialog = ForEmptyJobsDialog(::openAddActivity)

    override fun receivingPoem(login: String) {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        val refReceivingPoem =
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
                .child("MyJob")
        refReceivingPoem.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val children = p0.children
                var poem: PoemAnswer? = PoemAnswer()
                children.forEach {
                    poem = it.getValue(PoemAnswer::class.java)
                }
                if (poem == null) {
                    viewState.showDialog(emptyMyJobsDialog)
                } else {
                    viewState.goToJobUserFragment()
                }
            }
        })
    }

    override fun changeUsernameAll(model: String) {
        array.forEach {
            val refChangeUsernameMyJob =
                FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
                    .child("MyJob")
                    .child(it)
                    .child("username")
            refChangeUsernameMyJob.setValue(model)
        }
    }

    override fun receivingPoemCatalog(login: String, uid: String) {
        val refReceivingPoem =
            FirebaseDatabase.getInstance().reference.child(uid).child("Poems")

        refReceivingPoem.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val children = p0.children
                    children.forEach {
                        val poem: PoemAnswer? = it.getValue(PoemAnswer::class.java)
                        arrayCatalog += poem!!.id
                    }
                    changeUsernameAllCatalog(login, uid)
                }
            }
        })
    }

    override fun changeUsernameAllCatalog(model: String, uid: String) {
        arrayCatalog.forEach {
            val refChangeUsernameCatalog =
                FirebaseDatabase.getInstance().reference.child(uid).child("Poems")
                    .child(it)
                    .child("username")
            refChangeUsernameCatalog.setValue(model)
        }
    }

    override fun receivingPoemPoems(login: String, uid: String) {
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
                        if (poem!!.uid == uid)
                            arrayPoem += poem.id
                    }
                    changeUsernameAllPoem(login)
                }
            }
        })
    }

    override fun changeUsernameAllPoem(model: String) {
        array.forEach {
            val refChangeUsernamePoem =
                FirebaseDatabase.getInstance().reference.child("Poem")
                    .child(it)
                    .child("username")
            refChangeUsernamePoem.setValue(model)
        }
    }

    override fun openAddActivity() {
        viewState.openAddActivity()
    }
}