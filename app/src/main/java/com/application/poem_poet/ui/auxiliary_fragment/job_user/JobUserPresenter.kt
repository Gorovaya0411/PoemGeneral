package com.application.poem_poet.ui.auxiliary_fragment.job_user

import com.application.poem_poet.dialogFragments.ForDeleteMyDialog
import com.application.poem_poet.dialogFragments.ForEmptyJobsDialog
import com.application.poem_poet.model.PoemAnswer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class JobUserPresenter @Inject constructor() : JobUserPresenterImpl() {

    var firebaseUser: FirebaseUser? = null
    var listPoemPoet: MutableList<PoemAnswer?> = mutableListOf()
    private val emptyMyJobsDialog = ForEmptyJobsDialog(::openAddActivity)
    private val myAdapter =
        AdapterJobUser(::onClick)
    private lateinit var currentId: String
    private val deleteDialog = ForDeleteMyDialog(::functionDelete)

    override fun getData() {
        viewState.workWithAdapter(myAdapter)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        val refUser =
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
                .child("MyJob")

        refUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val listMyJob: PoemAnswer? = p0.getValue(PoemAnswer::class.java)
                val children = p0.children
                if (listMyJob == null) {
                    viewState.openDialog(emptyMyJobsDialog)
                }
                children.forEach {
                    val poem: PoemAnswer? = it.getValue(PoemAnswer::class.java)
                    listPoemPoet.add(poem)
                }
                populateData(listPoemPoet)
            }
        })
    }

    override fun populateData(poems: MutableList<PoemAnswer?>) {
        myAdapter.setData(poems)
    }

    override fun openAddActivity() {
        viewState.openAddActivity()
    }

    override fun onClick(model: PoemAnswer, mode: Int) {
        currentId = model.id
        if (mode == 1) {
            openingNewActivity(model)
        } else {
            viewState.openDeleteDialog(deleteDialog)
        }
    }

    override fun openingNewActivity(model: PoemAnswer) {
        viewState.openingNewActivity(model)
    }

    override fun functionDelete() {
        FirebaseDatabase.getInstance().reference.child("Poem").child(currentId).removeValue()
        FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
            .child("MyJob").child(currentId).removeValue()
        listPoemPoet.clear()
        getData()
        viewState.toast()
    }
}