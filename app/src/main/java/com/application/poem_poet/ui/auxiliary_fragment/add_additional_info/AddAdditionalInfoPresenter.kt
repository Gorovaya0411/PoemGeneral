package com.application.poem_poet.ui.auxiliary_fragment.add_additional_info

import android.widget.EditText
import android.widget.Toast
import com.application.poem_poet.model.Bio
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.ui.community.CommunityActivity
import com.google.firebase.database.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import javax.inject.Inject

class AddAdditionalInfoPresenter @Inject constructor() : AddAdditionalInfoImpl() {

    private lateinit var receivedAvatar: String
    private lateinit var refBio: DatabaseReference
    private lateinit var bio: String
    var array = emptyArray<String>()

    override fun getAvatar(id: String) {
        val refBio = FirebaseDatabase.getInstance().reference.child("Poem")
            .child(id)
        refBio.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val avatar: PoemAnswer? = p0.getValue(PoemAnswer::class.java)
                    receivedAvatar = avatar!!.avatar
                    if (receivedAvatar != "https://firebasestorage.googleapis.com/v0/b/poemspoets-130cd.appspot.com/o/icon.png?alt=media&token=5935d9cc-88cf-4697-8ed3-17abd66e9fee") {
                        viewState.workDataChange(receivedAvatar)
                    }
                }
            }
        })
    }

    override fun getBio(namePoet: String, addAdditionalInfoBioEditTxt: EditText) {
        val refBio =
            FirebaseDatabase.getInstance().reference.child(namePoet)
        refBio.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val biography: Bio? = p0.getValue(Bio::class.java)
                    bio = biography!!.biography
                    if (bio != "") {
                        addAdditionalInfoBioEditTxt.setText(bio)
                        addAdditionalInfoBioEditTxt.isEnabled = false
                    }
                }
            }
        })
    }

    override fun addBio(
        namePoet: String,
        model: CommunityActivity,
        addAdditionalInfoBioEditTxt: EditText
    ) {
        val bio: String = addAdditionalInfoBioEditTxt.text.toString()
        val userHashMap = HashMap<String, Any>()
        userHashMap["biography"] = bio

        refBio =
            FirebaseDatabase.getInstance().reference.child(namePoet)

        refBio.updateChildren(userHashMap)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        model,
                        "Эти данные будут добавлены!\nСпасибо)",
                        Toast.LENGTH_LONG
                    ).show()
                    viewState.broadcastData()
                } else {
                    Toast.makeText(
                        model,
                        "Ошибка:" + it.exception!!.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    override fun changePhotoUser(model: CommunityActivity) {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(700, 700)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(model)
    }
}