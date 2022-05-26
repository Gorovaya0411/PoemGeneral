package com.application.poem_poet.ui.community

import com.application.poem_poet.model.PoemHelp
import com.application.poem_poet.model.UserGeneralSave
import moxy.MvpPresenter
import moxy.MvpView

interface CommunityActivityView : MvpView

abstract class CommunityActivityPresenter : MvpPresenter<CommunityActivityView>() {
    abstract fun receivingPoemUser(id: String)
    abstract fun receivingPoemPoet(namePoet: String, id: String)
    abstract fun getCheckDetailedFragment(): String?
    abstract fun setCheckDetailedFragment(mark: String?)
    abstract fun getCheckCropFragment(): String?
    abstract fun setCheckCropFragment(mark: String?)
    abstract fun changeAvatarAllAdd(photo: String, id: String)
    abstract fun changeAvatarAll(photo: String, id: String)
    abstract fun getSavePoemHelp(): PoemHelp
    abstract fun setSavePoemHelp(poem: PoemHelp)
    abstract fun getSaveUserGeneral(): UserGeneralSave
    abstract fun setSaveUserGeneral(userGeneral: UserGeneralSave)
    abstract fun getUidUser(): String?
    abstract fun setUidUser(uid: String?)
}