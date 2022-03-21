package com.application.poem_poet.ui.community

import com.application.poem_poet.domain.CommunityUseCase
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.User
import com.google.firebase.database.*
import javax.inject.Inject

class CommunityPresenter @Inject constructor(
    private val mainUseCase: CommunityUseCase
) : CommunityActivityPresenter() {

    var arrayUser = emptyArray<String>()
    var arrayPoet = emptyArray<String>()
    var arrayUserAdd = emptyArray<String>()
    var arrayPoetAdd = emptyArray<String>()

    override fun getCheckDetailedFragment(): String? {
        return mainUseCase.checkDetailedFragment
    }

    override fun setCheckDetailedFragment(mark: String?) {
        mainUseCase.checkDetailedFragment = mark
    }

    override fun getCheckCropFragment(): String? {
        return mainUseCase.checkCropFragment
    }

    override fun setCheckCropFragment(mark: String?) {
        mainUseCase.checkCropFragment = mark
    }

    override fun getSavePoemAnswer(): PoemAnswer {
        return mainUseCase.savePoemAnswer
    }

    override fun setSavePoemAnswer(poem: PoemAnswer) {
        mainUseCase.savePoemAnswer = poem
    }

    override fun getSaveUser(): User {
        return mainUseCase.saveUser
    }

    override fun setSaveUser(user: User) {
        mainUseCase.saveUser = user
    }

    override fun receivingPoemUser(login: String, id: String) {
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
                        if (poem!!.username == login && poem.namePoet == "") {
                            arrayUser += poem.id
                        }
                    }
                }
            }
        })

        val refReceivingPoemAdd =
            FirebaseDatabase.getInstance().reference.child("Users").child(id).child("MyAdded")
        refReceivingPoemAdd.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val children = p0.children
                    children.forEach {
                        val poem: PoemAnswer? = it.getValue(PoemAnswer::class.java)
                        if (poem!!.username == login && poem.namePoet == "") {
                            arrayUserAdd += poem.id
                        }
                    }
                }
            }
        })
    }

    override fun receivingPoemPoet(namePoet: String, id: String) {
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
                        if (poem != null) {
                            if (poem.namePoet == namePoet) {
                                arrayPoet += poem.id
                            }
                        }
                    }
                }
            }
        })

        val refReceivingPoemAdd =
            FirebaseDatabase.getInstance().reference.child("Users").child(id).child("MyAdded")
        refReceivingPoemAdd.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val children = p0.children
                    children.forEach {
                        val poem: PoemAnswer? = it.getValue(PoemAnswer::class.java)
                        if (poem != null) {
                            if (poem.namePoet == namePoet) {
                                arrayPoetAdd += poem.id
                            }
                        }
                    }
                }
            }
        })
    }

    override fun changeAvatarAll(photo: String, id: String) {
        arrayUser.forEach {
            val refChangeAvatarAll =
                FirebaseDatabase.getInstance().reference.child("Users").child(id).child("MyAdded")
                    .child(it).child("avatar")
            refChangeAvatarAll.setValue(photo)
        }

        arrayUserAdd.forEach {
            val refChangeAvatarAll =
                FirebaseDatabase.getInstance().reference.child("Poem").child(it)
                    .child("avatar")
            refChangeAvatarAll.setValue(photo)
        }
    }

    override fun changeAvatarAllAdd(photo: String, id: String) {
        arrayPoet.forEach {
            val refChangeAvatarAll =
                FirebaseDatabase.getInstance().reference.child("Poem").child(it)
                    .child("avatar")
            refChangeAvatarAll.setValue(photo)
        }
        arrayPoetAdd.forEach {
            val refChangeAvatarAll =
                FirebaseDatabase.getInstance().reference.child("Users").child(id).child("MyAdded")
                    .child(it).child("avatar")
            refChangeAvatarAll.setValue(photo)
        }
    }
}