package com.application.poem_poet.ui.general_navigation.my_poem

import com.application.poem_poet.dialogFragments.ForDeleteMyDialog
import com.application.poem_poet.dialogFragments.ForEmptyMyPoemDialog
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.repository.CharactersDetailedRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MyPoemPresenter @Inject constructor(private val charactersMainUseCase: CharactersDetailedRepository) :
    MyPoemViewImpl() {
    private var firebaseUser: FirebaseUser? = null
    private var listPoemPoet: MutableList<PoemAnswer?> = mutableListOf()
    private val myAdapter =
        AdapterMyPoem(::onClick)
    private val emptyMyPoemDialog = ForEmptyMyPoemDialog(::openListPoemActivity)
    private val deleteDialog = ForDeleteMyDialog(::functionDelete)
    private lateinit var currentId: String

    override fun getData() {
        viewState.workWithAdapter(myAdapter)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        val refUser =
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
                .child("MyAdded")
        refUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val listMyPoem: PoemAnswer? = p0.getValue(PoemAnswer::class.java)
                val children = p0.children
                if (listMyPoem == null) {
                    viewState.showDialog(emptyMyPoemDialog)
                }
                children.forEach {
                    val poem: PoemAnswer? = it.getValue(PoemAnswer::class.java)
                    getCharactersByID(poem)
                }
            }
        })
        getAllCharacters()
    }

    override fun populateData(poems: MutableList<PoemAnswer?>) {
        myAdapter.setData(poems)
    }

    fun getCharactersByID(id: PoemAnswer?) {
        val disposable =
            charactersMainUseCase.getCharactersByID(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                }, {

                })
    }

    override fun onClick(model: PoemAnswer, mode: Int) {
        currentId = model.id
        if (mode == 1) {
            openingNewActivity(model)
        } else {
            viewState.showDeleteDialog(deleteDialog)
        }
    }

    private fun getAllCharacters() {
        val disposable = charactersMainUseCase.getAllCharacters().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                populateData(it)
            }, {

            })
    }

    override fun openingNewActivity(model: PoemAnswer) {
        viewState.openingNewActivity(model)
    }

    override fun openListPoemActivity() {
        viewState.openListPoemActivity()
    }

    override fun functionDelete() {
        val refId =
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
                .child("MyAdded").child(currentId)
        refId.setValue(null)
        listPoemPoet.clear()
        getData()
        viewState.showToast()
    }
}