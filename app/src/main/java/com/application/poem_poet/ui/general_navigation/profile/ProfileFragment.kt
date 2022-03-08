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
import com.application.poem_poet.model.User
import com.application.poem_poet.ui.community.CommunityActivity
import com.application.poem_poet.ui.job_user.JobUserPresenter
import com.application.poem_poet.ui.main.MainActivity
import com.application.poem_poet.utill.extension.launchActivityWithFinish
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter

class ProfileFragment : MvpAppCompatFragment(), ProfileView {

    @InjectPresenter
    lateinit var profilePresenter: ProfilePresenter

    private var firebaseUser: FirebaseUser? = null
    private val forOut = ForOutDialog(::logOut)
    private val contextActivity: CommunityActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as CommunityActivity)
    }
    lateinit var uid: String
    lateinit var binding: FragmentProfileBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        mAuth = FirebaseAuth.getInstance()
        binding = FragmentProfileBinding.bind(view)

        with(binding) {
            profileProgressBar.visibility = ProgressBar.INVISIBLE
            profilePresenter.addData()

            profileLoginTxt.setOnClickListener {
                showDialog(
                    "login",
                    "Изменить никнейм",
                    "Ваш новый никнейм",
                    profileLoginTxt
                )
            }
            profileChangePhotoBtn.setOnClickListener { changePhotoUser() }
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
                    "Изменить логин",
                    "Ваш новый логин",
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
            profileMyJobLinearLayout.setOnClickListener { profilePresenter.checkListMyJob() }
            profileGoOutTxt.setOnClickListener {
                forOut.show(
                    requireActivity().supportFragmentManager,
                    "ForDeleteMyPoemDialog"
                )
            }
        }
    }

    override fun showDialog(model: DialogFragment) {
        model.show(
            requireActivity().supportFragmentManager,
            "ForEmptyJobsDialog"
        )
    }

    override fun showElementsProfile(model: User?) {
        with(binding) {
            profileLoginTxt.text = model!!.login
            profileEmailTxt.text = model.email
            profileStatusTxt.text = model.status
            profileCommunicationTxt.text = model.address
            uid = model.uid
        }
    }

    override fun workWithAvatar(model: User?) {
        Picasso.get()
            .load(model!!.avatar)
            .into(binding.profileImageImg)
    }

    override fun workWithStatus() {
        binding.profileStatusTxt.text = getString(R.string.change_login)
    }

    override fun workWithAddress() {
        binding.profileCommunicationTxt.text = getString(R.string.communication_with_you)
    }

    private fun showDialog(mark: String, title: String, hint: String, view: TextView) {
        val input = EditText(context)

        input.hint = hint
        input.inputType = InputType.TYPE_CLASS_TEXT
        val builder: AlertDialog.Builder = AlertDialog.Builder(
            context,
            R.style.ThemeOverlay_AppCompat_Dialog_Alert_TestDialogTheme
        )
        with(builder) {
            setTitle(title)
            setView(R.layout.prompt)
            setPositiveButton("OK") { dialog, _ ->
                val result = input.text.toString()
                when (mark) {
                    "status" -> changeData(result, mark, view, "статус")
                    "address" -> changeData(result, mark, view, "текст")
                    "login" -> changeLogin(result)
                    "email" -> changeData(result, mark, view, "email")
                }
                dialog.cancel()
                findNavController().navigate(R.id.action_profileFragment_self)
            }
            setNegativeButton(
                "Cancel"
            ) { dialog, _ -> dialog.cancel() }
            show()
        }
    }

    private fun changeData(data: String, mark: String, view: TextView, userText: String) {
        val refDataUser =
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
                .child(mark)
        val refDataAll =
            FirebaseDatabase.getInstance().reference.child(uid).child(mark)

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
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
                .child("login")

        with(profilePresenter) {
            receivingPoem(data)
            receivingPoemCatalog(data, uid)
            receivingPoemPoems(data, uid)
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
