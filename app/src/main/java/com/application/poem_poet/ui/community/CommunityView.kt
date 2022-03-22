package com.application.poem_poet.ui.community

import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.User
import com.application.poem_poet.model.UserGeneral
import com.application.poem_poet.model.UserGeneralSave
import moxy.MvpPresenter
import moxy.MvpView

interface CommunityActivityView : MvpView

abstract class CommunityActivityPresenter : MvpPresenter<CommunityActivityView>() {
    abstract fun receivingPoemUser(login: String, id: String)
    abstract fun receivingPoemPoet(namePoet: String, id: String)
    abstract fun getCheckDetailedFragment(): String?
    abstract fun setCheckDetailedFragment(mark: String?)
    abstract fun getCheckCropFragment(): String?
    abstract fun setCheckCropFragment(mark: String?)
    abstract fun changeAvatarAllAdd(photo: String, id: String)
    abstract fun changeAvatarAll(photo: String, id: String)
    abstract fun getSavePoemAnswer(): PoemAnswer
    abstract fun setSavePoemAnswer(poem: PoemAnswer)
    abstract fun getSaveUser(): User
    abstract fun setSaveUser(user: User)
    abstract fun getSaveUserGeneral(): UserGeneralSave
    abstract fun setSaveUserGeneral(userGeneral: UserGeneralSave)
}