package com.application.poem_poet.ui.auxiliary_fragment.add_poem

import android.widget.Button
import android.widget.CheckBox
import com.application.poem_poet.model.*
import com.application.poem_poet.ui.community.CommunityActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import javax.inject.Inject

class AddPoemPresenter @Inject constructor() : AddPoemViewImpl() {
    var array: MutableList<String> = mutableListOf()
    private lateinit var refAllPoem: DatabaseReference
    private lateinit var refPoemUsers: DatabaseReference
    private lateinit var refPoemPoetOrUser: DatabaseReference
    private lateinit var refBio: DatabaseReference
    private var getAvatar: String? = null
    private lateinit var namePoetModified: String
    private var firebaseUser: FirebaseUser? = null
    private var login = ""
    private var avatarHere = ""
    private var uidHere = ""

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

    override fun addPoem(
        checkAdd: Boolean,
        model: CommunityActivity,
        poemAnswer: PoemHelp,
        addPoemCheckBox: CheckBox,
        addPoemAddBtn: Button
    ): Boolean {
        var checkAddEdit = checkAdd
        firebaseUser = FirebaseAuth.getInstance().currentUser
        refAllPoem =
            FirebaseDatabase.getInstance().reference.child("Poem")
                .push()
        refPoemUsers =
            FirebaseDatabase.getInstance().reference.child("Users")
                .child(firebaseUser!!.uid)
                .child("MyJob")
                .child(refAllPoem.key.toString())
        namePoetModified = poemAnswer.namePoet.replace(".", "|", true)
        if (poemAnswer.titlePoem == "") {
            checkAddEdit = true
            android.widget.Toast.makeText(
                model,
                "Введите название стиха",
                android.widget.Toast.LENGTH_LONG
            )
                .show()
        } else if (poemAnswer.genre == "") {
            checkAddEdit = true
            android.widget.Toast.makeText(
                model,
                "Введите жанр",
                android.widget.Toast.LENGTH_LONG
            ).show()
        } else if (poemAnswer.poem == "") {
            checkAddEdit = true
            android.widget.Toast.makeText(
                model,
                "Введите стих",
                android.widget.Toast.LENGTH_LONG
            ).show()
        } else if (namePoetModified == "" && !addPoemCheckBox.isChecked) {
            checkAddEdit = true
            android.widget.Toast.makeText(
                model,
                "Введите имя автора",
                android.widget.Toast.LENGTH_LONG
            ).show()
        } else {
            addPoemAddBtn.setBackgroundResource(com.application.poem_poet.R.drawable.ic_add_poem_add_select)
            addAvatar(poemAnswer.namePoet, poemAnswer.avatar)
            if (namePoetModified == "") {
                refPoemPoetOrUser =
                    FirebaseDatabase.getInstance().reference.child(
                        poemAnswer.uid
                    ).child("Poems")
                        .child(refAllPoem.key.toString())

            } else {
                refPoemPoetOrUser =
                    FirebaseDatabase.getInstance().reference.child(
                        namePoetModified
                    )
                        .child("Poems")
                        .child(refAllPoem.key.toString())
                refBio =
                    FirebaseDatabase.getInstance().reference.child(
                        namePoetModified
                    )
                        .child("biography")
                refBio.setValue("")
            }

            val poemHashMap = HashMap<String, Any>()
            poemHashMap["titlePoem"] = poemAnswer.titlePoem
            poemHashMap["namePoet"] = namePoetModified
            poemHashMap["username"] = poemAnswer.username
            poemHashMap["genre"] = poemAnswer.genre
            poemHashMap["poem"] = poemAnswer.poem
            poemHashMap["id"] = refAllPoem.key.toString()
            poemHashMap["uid"] = poemAnswer.uid
            poemHashMap["like"] = 0

            refPoemUsers.updateChildren(poemHashMap)
            refAllPoem.updateChildren(poemHashMap)
            refPoemPoetOrUser.updateChildren(poemHashMap)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        android.widget.Toast.makeText(
                            model,
                            "Ваш стих добавлен!:)",
                            android.widget.Toast.LENGTH_LONG
                        )
                            .show()
                        viewState.openGeneralPoets()
                        checkAddEdit = true
                    } else {
                        android.widget.Toast.makeText(
                            model,
                            "Ошибка:" + it.exception!!.message.toString(),
                            android.widget.Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
        return checkAddEdit
    }

    private fun addAvatar(namePoet: String, avatarMove: String) {
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
                if (namePoet == "") {
                    poemHashMap["avatar"] = avatarMove
                } else {
                    if (getAvatar == "") {
                        poemHashMap["avatar"] =
                            "https://firebasestorage.googleapis.com/v0/b/poemspoets-130cd.appspot.com/o/icon.png?alt=media&token=5935d9cc-88cf-4697-8ed3-17abd66e9fee"
                    } else {
                        poemHashMap["avatar"] =
                            getAvatar!!
                    }
                }
                refPoemUsers.updateChildren(poemHashMap)
                refAllPoem.updateChildren(poemHashMap)
                refPoemPoetOrUser.updateChildren(poemHashMap)
            }
        })
    }


    override fun addUser(): WorkAddUser {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        val refUser =
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        refUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val user: User? = p0.getValue(User::class.java)
                    login = user!!.login
                    uidHere = user.uid
                    avatarHere = user.avatar

                }
            }
        })
        return WorkAddUser(login, uidHere, avatarHere)
    }
}