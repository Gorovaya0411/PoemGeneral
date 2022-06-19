package com.application.poem_poet.ui.community

import com.application.poem_poet.domain.CommunityUseCase
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.PoemHelp
import com.application.poem_poet.model.UserGeneralSave
import com.google.firebase.database.*
import javax.inject.Inject

class CommunityPresenter @Inject constructor(
    private val communityUseCase: CommunityUseCase
) : CommunityActivityPresenter() {

    var arrayUser = emptyArray<String>()
    var arrayPoet = emptyArray<String>()
    var arrayUserAdd = emptyArray<String>()
    var arrayPoetAdd = emptyArray<String>()

    override fun getCheckDetailedFragment(): String? {
        return communityUseCase.checkDetailedFragment
    }

    override fun setCheckDetailedFragment(mark: String?) {
        communityUseCase.checkDetailedFragment = mark
    }

    override fun getUidUser(): String? {
        return communityUseCase.saveUidUser
    }

    override fun setUidUser(uid: String?) {
        communityUseCase.saveUidUser = uid
    }

    override fun getCheckCropFragment(): String? {
        return communityUseCase.checkCropFragment
    }

    override fun setCheckCropFragment(mark: String?) {
        communityUseCase.checkCropFragment = mark
    }

    override fun getSavePoemHelp(): PoemHelp {
        return communityUseCase.savePoemHelp
    }

    override fun setSavePoemHelp(poem: PoemHelp) {
        communityUseCase.savePoemHelp = poem
    }

    override fun getSaveUserGeneral(): UserGeneralSave {
        return communityUseCase.saveUserGeneral
    }

    override fun setSaveUserGeneral(userGeneral: UserGeneralSave) {
        communityUseCase.saveUserGeneral = userGeneral
    }

    override fun receivingPoemUser(id: String) {
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
                        if (poem!!.uid == id && poem.namePoet == "") {
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
                        if (poem!!.uid == id && poem.namePoet == "") {
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
                                arrayPoet += poem.namePoet
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
                                arrayPoetAdd += poem.namePoet
                            }
                        }
                    }
                }
            }
        })
    }

    override fun changeAvatarAll(photo: String, id: String) {
        arrayUserAdd.forEach {
            val refChangeAvatarAll =
                FirebaseDatabase.getInstance().reference.child("Users").child(id).child("MyAdded")
                    .child(it).child("avatar")
            refChangeAvatarAll.setValue(photo)
        }

        arrayUser.forEach {
            val refChangeAvatarAll =
                FirebaseDatabase.getInstance().reference.child("Poem").child(it)
                    .child("avatar")
            refChangeAvatarAll.setValue(photo)
        }
    }

    override fun changeAvatarAllAdd(photo: String, id: String) {
        arrayPoet.forEach { _ ->
            val refChangeAvatarAll =
                FirebaseDatabase.getInstance().reference.child("Poem").child(id)
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