package com.application.poem_poet.ui.general_navigation.poets.navigation.poets

import com.application.poem_poet.model.PoemAnswer
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface ListPoetsView : MvpView {
    fun workWithSearchWidget(model: AdapterListPoets)
    fun openingNewActivity(model: PoemAnswer)
    fun workWithAdapter(model: AdapterListPoets)
}