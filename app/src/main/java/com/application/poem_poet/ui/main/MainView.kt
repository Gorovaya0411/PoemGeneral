package com.application.poem_poet.ui.main

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface MainView : MvpView

abstract class MainActivityPresenterImpl : MvpPresenter<MainView>()