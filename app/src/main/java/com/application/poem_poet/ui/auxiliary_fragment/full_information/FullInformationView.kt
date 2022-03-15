package com.application.poem_poet.ui.auxiliary_fragment.full_information

import com.application.poem_poet.model.PoemAnswer
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface FullInformationView : MvpView {
    fun populateData(listPoemPoet:MutableList<PoemAnswer?>)
    fun convertData()
}

abstract class FullInformationImpl : MvpPresenter<FullInformationView>() {

}