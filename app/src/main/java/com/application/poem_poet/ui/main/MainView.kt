package com.application.poem_poet.ui.main

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface MainView : MvpView

abstract class MainActivityPresenter : MvpPresenter<MainView>() {
    abstract fun getUidUser(): String?
    abstract fun setUidUser(uid: String?)
}