package com.application.poem_poet.ui.general_navigation.poets.navigation.poets

import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.ui.community.CommunityActivity
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface ListPoetsView : MvpView {
    fun workWithSearchWidget(model: AdapterListPoets)
    fun openingNewActivity(model: PoemAnswer)
    fun workWithAdapter(model: AdapterListPoets)
}

abstract class ListPoetPresenterImpl : MvpPresenter<ListPoetsView>() {
    abstract fun getData(model: CommunityActivity)
    abstract fun populateData(poems: MutableList<PoemAnswer?>)
    abstract fun openingNewActivity(model: PoemAnswer)
}