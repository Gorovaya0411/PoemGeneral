package com.application.poem_poet.ui.greeting_user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.application.poem_poet.databinding.FragmentLoginBinding
import com.application.poem_poet.ui.base.BaseFragment
import com.application.poem_poet.ui.community.CommunityActivity
import com.application.poem_poet.ui.main.MainActivity
import com.application.poem_poet.utill.extension.launchActivityWithFinish
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private lateinit var mAuth: FirebaseAuth

    private val contextActivity: MainActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        binding.loginEntranceBtn.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        with(binding) {
            val email: String = loginEmailEditText.text.toString()
            val password: String = loginPasswordEditText.text.toString()

            when {
                email == "" -> Toast.makeText(
                    contextActivity,
                    "Введите E-mail",
                    Toast.LENGTH_LONG
                )
                    .show()
                password == "" -> Toast.makeText(
                    contextActivity,
                    "Введите пароль",
                    Toast.LENGTH_LONG
                ).show()
                else ->
                    mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                launchActivityWithFinish<CommunityActivity>(contextActivity)
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

    override fun initViewBinding() = FragmentLoginBinding.inflate(layoutInflater)
}