package com.application.poem_poet.ui.auxiliary_fragment

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentAddPoemBinding
import com.application.poem_poet.model.Bio
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.User
import com.application.poem_poet.ui.base.BaseFragment
import com.application.poem_poet.ui.community.CommunityActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class AddPoemFragment : BaseFragment<FragmentAddPoemBinding>() {
    private lateinit var refAllPoem: DatabaseReference
    private lateinit var refPoemUsers: DatabaseReference
    private lateinit var refPoemPoetOrUser: DatabaseReference
    private lateinit var login: String
    private lateinit var refBio: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private var firebaseUser: FirebaseUser? = null
    lateinit var uid: String
    lateinit var avatar: String
    var array: MutableList<String> = mutableListOf()
    private lateinit var namePoet: String
    private var getAvatar: String? = null
    private lateinit var namePoetModified: String
    private val contextActivity: CommunityActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as CommunityActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser

        with(binding) {
            addPoemNameTxt.visibility = TextView.INVISIBLE

            receivingNamePoet()
            addUser()

            addPoemCheckBox.setOnCheckedChangeListener { _, isChecked ->

                if (!isChecked) {
                    addPoemNameTxt.visibility = TextView.INVISIBLE
                    addPoemAutoCompleteTxt.visibility = AutoCompleteTextView.VISIBLE
                } else {
                    addPoemAutoCompleteTxt.visibility = AutoCompleteTextView.INVISIBLE
                    addPoemAutoCompleteTxt.setText("")
                    addPoemNameTxt.visibility = TextView.VISIBLE
                    addPoemNameTxt.text = login

                }
            }

            addPoemAddBtn.setOnClickListener {
                addPoem()
            }

            addPoemBackImg.setOnClickListener {
                findNavController().navigate(R.id.action_addPoemFragment_to_generalPoetsFragment)
            }
        }
    }

    private fun receivingNamePoet() {
        val refReceivingPoem =
            FirebaseDatabase.getInstance().reference.child("Poem")
        refReceivingPoem.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val children = p0.children
                    val adapter = ArrayAdapter(
                        contextActivity, android.R.layout.simple_dropdown_item_1line, array
                    )
                    children.forEach {
                        with(binding) {
                            val poem: PoemAnswer? = it.getValue(PoemAnswer::class.java)
                            if (poem!!.namePoet != "") {
                                val namePoetModified = poem.namePoet.replace("|", ".", true)
                                array.add(namePoetModified)
                                val set: Set<String> = HashSet(array)
                                array.clear()
                                array.addAll(set)
                            }

                            addPoemAutoCompleteTxt.setAdapter(adapter)
                            addPoemAutoCompleteTxt.threshold = 1
                            addPoemAutoCompleteTxt.onItemClickListener =
                                AdapterView.OnItemClickListener { parent, _,
                                                                  position, _ ->
                                    val selectedItem = parent.getItemAtPosition(position).toString()
                                    namePoet = selectedItem
                                }
                        }
                    }
                }
            }
        })
    }

    private fun addPoem() {

        with(binding) {
            val titlePoem = addPoemTitleEditTxt.text.toString()
            val genre = addPoemGenreEditTxt.text.toString()
            val poem = addPoemPoemEditTxt.text.toString()
            val username = login

            refAllPoem = FirebaseDatabase.getInstance().reference.child("Poem").push()
            refPoemUsers =
                FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
                    .child("MyJob")
                    .child(refAllPoem.key.toString())
            namePoet = addPoemAutoCompleteTxt.text.toString()
            namePoetModified = namePoet.replace(".", "|", true)

            if (titlePoem == "") {
                Toast.makeText(contextActivity, "Введите название стиха", Toast.LENGTH_LONG)
                    .show()
            } else if (genre == "") {
                Toast.makeText(contextActivity, "Введите жанр", Toast.LENGTH_LONG).show()
            } else if (poem == "") {
                Toast.makeText(contextActivity, "Введите стих", Toast.LENGTH_LONG).show()
            } else if (namePoetModified == "" && !addPoemCheckBox.isChecked) {
                Toast.makeText(
                    contextActivity,
                    "Введите имя автора",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                addPoemAddBtn.setBackgroundResource(R.drawable.ic_add_poem_add_select)
                addAvatar()
                if (namePoetModified == "") {
                    refPoemPoetOrUser =
                        FirebaseDatabase.getInstance().reference.child(uid).child("Poems")
                            .child(refAllPoem.key.toString())

                } else {
                    refPoemPoetOrUser =
                        FirebaseDatabase.getInstance().reference.child(namePoetModified)
                            .child("Poems")
                            .child(refAllPoem.key.toString())
                    refBio = FirebaseDatabase.getInstance().reference.child(namePoetModified)
                        .child("biography")
                    refBio.setValue("")
                }

                val poemHashMap = HashMap<String, Any>()
                poemHashMap["titlePoem"] = titlePoem
                poemHashMap["namePoet"] = namePoetModified
                poemHashMap["username"] = username
                poemHashMap["genre"] = genre
                poemHashMap["poem"] = poem
                poemHashMap["id"] = refAllPoem.key.toString()
                poemHashMap["uid"] = uid
                poemHashMap["like"] = 0

                refPoemUsers.updateChildren(poemHashMap)
                refAllPoem.updateChildren(poemHashMap)
                refPoemPoetOrUser.updateChildren(poemHashMap)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                contextActivity,
                                "Ваш стих добавлен!:)",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            findNavController().navigate(R.id.action_addPoemFragment_to_generalPoetsFragment)
                        } else {
                            Toast.makeText(
                                contextActivity,
                                "Ошибка:" + it.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }

    private fun addAvatar() {
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
                    poemHashMap["avatar"] = avatar
                } else {
                    if (getAvatar == "") {
                        poemHashMap["avatar"] = "https://firebasestorage.googleapis.com/v0/b/poemspoets-130cd.appspot.com/o/icon.png?alt=media&token=5935d9cc-88cf-4697-8ed3-17abd66e9fee"
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


    private fun addUser() {

        val refUser =
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        refUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val user: User? = p0.getValue(User::class.java)
                    login = user!!.login
                    uid = user.uid
                    avatar = user.avatar
                }
            }
        })
    }

    override fun initViewBinding() = FragmentAddPoemBinding.inflate(layoutInflater)
}
