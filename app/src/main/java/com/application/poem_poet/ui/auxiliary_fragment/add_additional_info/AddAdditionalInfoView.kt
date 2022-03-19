package com.application.poem_poet.ui.auxiliary_fragment.add_additional_info

import android.widget.EditText
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.ui.community.CommunityActivity
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface AddAdditionalInfoView : MvpView {
    fun workDataChange(receivedAvatar: String)
    fun broadcastData()
}

abstract class AddAdditionalInfoImpl : MvpPresenter<AddAdditionalInfoView>() {
    abstract fun getAvatar(id: String)
    abstract fun getBio(namePoet: String, addAdditionalInfoBioEditTxt: EditText)
    abstract fun addBio(
        namePoet: String,
        model: CommunityActivity,
        addAdditionalInfoBioEditTxt: EditText
    )
    abstract fun changePhotoUser(model: CommunityActivity)
}