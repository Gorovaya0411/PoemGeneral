package com.application.poem_poet.ui.general_navigation.profile

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentProfileBinding
import com.application.poem_poet.dialogFragments.ForOutDialog
import com.application.poem_poet.model.UserGeneralSave
import com.application.poem_poet.ui.community.CommunityActivity
import com.application.poem_poet.ui.main.MainActivity
import com.application.poem_poet.utill.extension.launchActivityWithFinish
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class ProfileFragment : MvpAppCompatFragment(), ProfileView {

    @InjectPresenter
    lateinit var profilePresenter: ProfilePresenter

    private val forOut = ForOutDialog(::logOut)
    private val contextActivity: CommunityActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as CommunityActivity)
    }
    lateinit var binding: FragmentProfileBinding
    private lateinit var mAuth: FirebaseAuth
    private var checkMyJob = true
    private var checkOut = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        binding = FragmentProfileBinding.bind(view)

        with(binding) {
            profileProgressBar.visibility = ProgressBar.INVISIBLE
            showElementsProfile()

            profileLoginTxt.setOnClickListener {
                showDialog(
                    "login",
                    "Изменить никнейм",
                    "Ваш новый никнейм",
                    profileLoginTxt
                )
            }
            profileChangePhotoBtn.setOnClickListener {
                contextActivity.communityPresenter.setCheckCropFragment("profile")
                changePhotoUser()
            }
            profileStatusTxt.setOnClickListener {
                showDialog(
                    "status",
                    "О чем вы думаете?",
                    "Ваш статус",
                    profileStatusTxt
                )
            }
            profileCommunicationTxt.setOnClickListener {
                showDialog(
                    "address",
                    "Добавить как можно с вами связаться",
                    "Добавлено",
                    profileCommunicationTxt
                )
            }
            profileEmailTxt.setOnClickListener {
                showDialog(
                    "email",
                    "Изменить ваш e-mail",
                    "Ваш новый e-mail",
                    profileEmailTxt
                )
            }
            profileMyJobLinearLayout.setOnClickListener {
                if (checkMyJob) {
                    checkMyJob = false
                    profilePresenter.receivingPoem(contextActivity.communityPresenter.getSaveUserGeneral().login)
                }
            }
            profileGoOutTxt.setOnClickListener {
                if (checkOut) {
                    checkOut = false
                    forOut.show(
                        requireActivity().supportFragmentManager,
                        "ForDeleteMyPoemDialog"
                    )
                }
            }
        }
    }

    override fun showDialog(model: DialogFragment) {
        checkMyJob = true
        model.show(
            requireActivity().supportFragmentManager,
            "ForEmptyJobsDialog"
        )
    }

    private fun showElementsProfile() {
        with(binding) {
            with(contextActivity.communityPresenter.getSaveUserGeneral()) {
                profileLoginTxt.text = login
                profileEmailTxt.text = email
                profileStatusTxt.text = status
                profileCommunicationTxt.text = address
                Picasso.get()
                    .load(avatar)
                    .into(binding.profileImageImg)
                if (status == "") {
                    binding.profileStatusTxt.text = getString(R.string.change_login)
                }
                if (address == "") {
                    binding.profileCommunicationTxt.text =
                        getString(R.string.communication_with_you)
                }
            }
        }
    }

    private fun showDialog(mark: String, title: String, hint: String, view: TextView) {
        with(contextActivity.communityPresenter) {
            val input = EditText(contextActivity)
            input.hint = hint
            input.inputType = InputType.TYPE_CLASS_TEXT
            val builder: AlertDialog.Builder = AlertDialog.Builder(contextActivity)
            builder.setTitle(title)
            builder.setMessage(hint)
            builder.setView(input)
            builder.setPositiveButton("OK") { dialog, _ ->
                val result = input.text.toString()
                when (mark) {
                    "status" -> {
                        changeData(result, mark, view, "статус")
                        setSaveUserGeneral(
                            UserGeneralSave(
                                getSaveUserGeneral().email,
                                getSaveUserGeneral().login,
                                getSaveUserGeneral().avatar,
                                result,
                                getSaveUserGeneral().address,
                                getSaveUserGeneral().uid
                            )
                        )
                    }
                    "address" -> {
                        changeData(result, mark, view, "текст")
                        setSaveUserGeneral(
                            UserGeneralSave(
                                getSaveUserGeneral().email,
                                getSaveUserGeneral().login,
                                getSaveUserGeneral().avatar,
                                getSaveUserGeneral().status,
                                result,
                                getSaveUserGeneral().uid
                            )
                        )
                    }
                    "login" -> {
                        changeLogin(result)
                        setSaveUserGeneral(
                            UserGeneralSave(
                                getSaveUserGeneral().email,
                                result,
                                getSaveUserGeneral().avatar,
                                getSaveUserGeneral().status,
                                getSaveUserGeneral().address,
                                getSaveUserGeneral().uid
                            )
                        )
                    }
                    "email" -> {
                        changeData(result, mark, view, "email")
                        setSaveUserGeneral(
                            UserGeneralSave(
                                result,
                                getSaveUserGeneral().login,
                                getSaveUserGeneral().avatar,
                                getSaveUserGeneral().status,
                                getSaveUserGeneral().address,
                                getSaveUserGeneral().uid
                            )
                        )
                    }
                }
                dialog.cancel()
                findNavController().navigate(R.id.action_profileFragment_self)
            }
            builder.setNegativeButton(
                "Cancel"
            ) { dialog, _ -> dialog.cancel() }
            builder.show()
        }
    }

    private fun changeData(data: String, mark: String, view: TextView, userText: String) {
        val refDataUser =
            FirebaseDatabase.getInstance().reference.child("Users")
                .child(contextActivity.communityPresenter.getSaveUserGeneral().uid)
                .child(mark)
        val refDataAll =
            FirebaseDatabase.getInstance().reference.child(contextActivity.communityPresenter.getSaveUserGeneral().uid)
                .child(mark)
        refDataUser.setValue(data)
        refDataAll.setValue(data)

        view.text = data

        Toast.makeText(
            context,
            getString(R.string.data_successfully_changed, userText),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun changeLogin(data: String) {
        val refLogin =
            FirebaseDatabase.getInstance().reference.child("Users")
                .child(contextActivity.communityPresenter.getSaveUserGeneral().uid)
                .child("login")

        with(profilePresenter) {
            receivingPoemCatalog(data, contextActivity.communityPresenter.getSaveUserGeneral().uid)
            receivingPoemPoems(data, contextActivity.communityPresenter.getSaveUserGeneral().uid)
        }

        if (data == "") {
            Toast.makeText(
                context,
                getString(R.string.line_remained_empty),
                Toast.LENGTH_LONG
            ).show()
        } else {
            refLogin.setValue(data)
            binding.profileLoginTxt.text = data
            Toast.makeText(
                context,
                getString(R.string.successfully_changed),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun logOut() {
        checkOut = true
        mAuth.signOut()
        launchActivityWithFinish<MainActivity>(contextActivity)
    }

    override fun openAddActivity() {
        findNavController().navigate(R.id.action_profileFragment_to_addPoemFragment)
    }

    override fun goToJobUserFragment() {
        findNavController().navigate(R.id.action_profileFragment_to_jobUserFragment)
    }

    private fun changePhotoUser() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(700, 700)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start((activity as CommunityActivity))
    }
}
