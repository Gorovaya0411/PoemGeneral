package com.application.poem_poet.ui.community

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface CommunityActivityView : MvpView

abstract class CommunityActivityPresenter : MvpPresenter<CommunityActivityView>() {
    abstract fun receivingName()
    abstract fun receivingPoem()
    abstract fun getCheckDetailedFragment(): String?
    abstract fun setCheckDetailedFragment(mark: String?)
}