package com.application.poem_poet.ui.community

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface CommunityActivityView : MvpView

abstract class CommunityActivityPresenter : MvpPresenter<CommunityActivityView>() {
    abstract fun receivingPoemUser(login: String, id:String)
    abstract fun receivingPoemPoet(namePoet: String, id:String)
    abstract fun getCheckDetailedFragment(): String?
    abstract fun setCheckDetailedFragment(mark: String?)
    abstract fun getCheckCropFragment(): String?
    abstract fun setCheckCropFragment(mark: String?)
    abstract fun setSaveNamePoetAddFragment(namePoet: String?)
    abstract fun getSaveNamePoetAddFragment(): String?
    abstract fun changeAvatarAllAdd(photo: String, id:String)
    abstract fun getSaveIdPoetAddFragment(): String?
    abstract fun setSaveIdPoetAddFragment(id: String?)
    abstract fun getSaveLoginUser(): String?
    abstract fun setSaveLoginUser(login: String?)
    abstract fun changeAvatarAll(photo: String, id:String)
    abstract fun setSaveIdUser(id: String?)
    abstract fun getSaveIdUser(): String?

}