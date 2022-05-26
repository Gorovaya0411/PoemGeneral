package com.application.poem_poet.ui.greeting_user

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.application.poem_poet.databinding.FragmentRegisterBinding
import com.application.poem_poet.ui.base.BaseFragment
import com.application.poem_poet.ui.community.CommunityActivity
import com.application.poem_poet.ui.main.MainActivity
import com.application.poem_poet.utill.extension.launchActivityWithFinish
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.HashMap

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val contextActivity: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }
    private lateinit var mAuth: FirebaseAuth
    private var firebaseUserID: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        binding.registerProgressBar.visibility = ProgressBar.INVISIBLE

        binding.registerBackImg.setOnClickListener {
            contextActivity.onBackPressed()
        }

        binding.registerRegistrationBtn.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        with(binding) {
            val login: String = registerUsernameEditText.text.toString()
            val email: String = registerEmailEditText.text.toString()
            val password: String = registerPasswordEditText.text.toString()
            val status = ""
            val address = ""

            when {
                login == "" ->
                    Toast.makeText(
                        contextActivity,
                        "Введите имя пользователя",
                        Toast.LENGTH_LONG
                    )
                        .show()
                email == "" ->
                    Toast.makeText(contextActivity, "Введите E-mail", Toast.LENGTH_LONG)
                        .show()
                password == "" ->
                    Toast.makeText(contextActivity, "Введите пароль", Toast.LENGTH_LONG)
                        .show()

                else ->
                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                registerProgressBar.visibility = ProgressBar.VISIBLE
                                registerRegistrationBtn.visibility = Button.INVISIBLE
                                firebaseUserID = mAuth.currentUser!!.uid
                                val refUsers =
                                    FirebaseDatabase.getInstance().reference.child("Users")
                                        .child(firebaseUserID)

                                val userHashMap = HashMap<String, Any>()
                                userHashMap["uid"] = firebaseUserID
                                userHashMap["email"] = email
                                userHashMap["login"] = login
                                userHashMap["avatar"] =
                                    "https://firebasestorage.googleapis.com/v0/b/poemspoets-130cd.appspot.com/o/icon2.png?alt=media&token=f241a65b-137c-47e2-b33c-800e6a6a1b3d"
                                userHashMap["search"] = login.toLowerCase(Locale.ROOT)
                                userHashMap["status"] = status
                                userHashMap["address"] = address

                                contextActivity.mainPresenter.setUidUser(firebaseUserID)

                                refUsers.updateChildren(userHashMap)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            launchActivityWithFinish<CommunityActivity>(
                                                contextActivity
                                            )
                                        }
                                    }

                            } else {
                                Toast.makeText(
                                    contextActivity,
                                    "Ошибка:" + task.exception!!.message.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
            }
        }
    }

    override fun initViewBinding() = FragmentRegisterBinding.inflate(layoutInflater)
}