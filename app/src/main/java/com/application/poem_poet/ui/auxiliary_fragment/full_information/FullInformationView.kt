package com.application.poem_poet.ui.auxiliary_fragment.full_information

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface FullInformationView : MvpView {

}

abstract class FullInformationImpl : MvpPresenter<FullInformationView>() {

}