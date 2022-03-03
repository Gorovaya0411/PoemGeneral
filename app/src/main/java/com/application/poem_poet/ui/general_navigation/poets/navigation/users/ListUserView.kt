package com.application.poem_poet.ui.general_navigation.poets.navigation.users

import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.ui.general_navigation.profile.ProfileView
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface ListUserView : MvpView {
    fun workWithSearchWidget(model: AdapterListUser)
    fun workWithAdapter(model: AdapterListUser)
    fun openingNewActivity(model: PoemAnswer)
}

abstract class ListUserPresenterImpl : MvpPresenter<ListUserView>() {
    abstract fun getData()
    abstract fun populateData(poems: MutableList<PoemAnswer?>)
    abstract fun openingNewActivity(model: PoemAnswer)
}