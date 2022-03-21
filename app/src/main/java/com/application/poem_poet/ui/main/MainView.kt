package com.application.poem_poet.ui.main

import com.application.poem_poet.model.User
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface MainView : MvpView

abstract class MainActivityPresenterImpl : MvpPresenter<MainView>() {
    abstract fun getSaveUser(): User
    abstract fun setSaveUser(user: User)
}