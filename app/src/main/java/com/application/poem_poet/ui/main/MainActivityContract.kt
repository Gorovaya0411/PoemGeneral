package com.application.poem_poet.ui.main

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MainActivityView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun goToWelcomeFragment()
}

abstract class MainActivityPresenter : MvpPresenter<MainActivityView>() {

}