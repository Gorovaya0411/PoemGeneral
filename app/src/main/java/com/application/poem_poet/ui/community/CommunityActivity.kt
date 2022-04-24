package com.application.poem_poet.ui.community

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.application.poem_poet.App
import com.application.poem_poet.R
import com.application.poem_poet.di.detailedModule.CommunityActivityModule
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.PoemHelp
import com.application.poem_poet.model.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.fragment_add_additional_info.*
import kotlinx.android.synthetic.main.fragment_profile.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class CommunityActivity : MvpAppCompatActivity(), CommunityActivityView {

    private var firebaseUser: FirebaseUser? = null
    private lateinit var refStorageRoot: StorageReference
    private var refChangeAvatarInUser: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        firebaseUser = FirebaseAuth.getInstance().currentUser
        refStorageRoot = FirebaseStorage.getInstance().reference
        refChangeAvatarInUser =
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
                .child("avatar")
    }

    fun openingNewActivity(poem: PoemAnswer) {
        communityPresenter.setSavePoemHelp(
            PoemHelp(
                poem.username,
                poem.titlePoem,
                poem.namePoet,
                poem.genre,
                poem.poem,
                poem.avatar,
                poem.id,
                poem.uid,
                poem.like
            )
        )
        val navHostFragment: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.community_fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(
            R.id.action_generalPoetsFragment_to_detailedPoemFragment
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (communityPresenter.getCheckCropFragment()) {
            "profile" -> profile_progress_bar.visibility = ProgressBar.VISIBLE
            "add" -> add_additional_info_progress_bar.visibility = ProgressBar.VISIBLE
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null
        ) {

            when (communityPresenter.getCheckCropFragment()) {
                "profile" -> {
                    communityPresenter.receivingPoemUser(
                        communityPresenter.getSaveUserGeneral().login,
                        communityPresenter.getSaveUserGeneral().uid
                    )
                    val uri = CropImage.getActivityResult(data).uri
                    val path = refStorageRoot.child("PhotoUser").child(firebaseUser!!.uid)
                    path.putFile(uri).addOnCompleteListener { task1 ->
                        if (task1.isSuccessful) {
                            path.downloadUrl.addOnCompleteListener { task2 ->
                                if (task2.isSuccessful) {
                                    val photoUrl = task2.result.toString()
                                    refChangeAvatarInUser!!.setValue(photoUrl)
                                    communityPresenter.changeAvatarAll(
                                        photoUrl,
                                        communityPresenter.getSaveUserGeneral().uid
                                    )
                                    Picasso.get()
                                        .load(photoUrl)
                                        .into(profile_image_img)
                                    profile_progress_bar.visibility = ProgressBar.INVISIBLE
                                }
                            }
                        }
                    }
                }
                "add" -> {
                    communityPresenter.receivingPoemPoet(
                        communityPresenter.getSavePoemHelp().namePoet,
                        communityPresenter.getSaveUserGeneral().uid
                    )
                    val uri = CropImage.getActivityResult(data).uri
                    val refAvatar =
                        FirebaseDatabase.getInstance().reference.child(communityPresenter.getSavePoemHelp().namePoet)
                            .child("avatar")
                    val path = refStorageRoot.child("PhotoUser")
                        .child(communityPresenter.getSavePoemHelp().namePoet)
                    path.putFile(uri).addOnCompleteListener { task1 ->
                        if (task1.isSuccessful)
                            path.downloadUrl.addOnCompleteListener { task2 ->
                                if (task2.isSuccessful) {
                                    val photoUrl = task2.result.toString()
                                    communityPresenter.changeAvatarAllAdd(
                                        photoUrl,
                                        communityPresenter.getSavePoemHelp().id
                                    )
                                    refAvatar.setValue(photoUrl)
                                    Picasso.get()
                                        .load(photoUrl)
                                        .into(add_additional_info_photo_img)
                                    add_additional_info_progress_bar.visibility =
                                        ProgressBar.INVISIBLE

                                    add_additional_info_change_photo_txt.visibility =
                                        TextView.INVISIBLE
                                }
                            }
                    }
                }
            }
        }
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    override fun onStart() {
        super.onStart()
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.community_bottom_navigation_view)
        val navController = findNavController(R.id.community_fragment_container_view)
        bottomNavigationView.setupWithNavController(navController)
        startPostponedEnterTransition()
    }

    @InjectPresenter
    lateinit var communityPresenter: CommunityPresenter

    @ProvidePresenter
    fun provideLandingActivityPresenter(): CommunityPresenter {
        return App.appComponent.inject(
            CommunityActivityModule()
        ).presenter
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }
    }
}