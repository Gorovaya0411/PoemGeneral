package com.application.poem_poet.ui.auxiliary_fragment.change_poem

import com.application.poem_poet.model.PoemHelp
import com.application.poem_poet.ui.community.CommunityActivity
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface ChangePoemView : MvpView {
    fun workWithAutoTxt(array: MutableList<String>)
    fun back()
}

abstract class ChangeViewImpl : MvpPresenter<ChangePoemView>() {
    abstract fun changePoem(
        id: String,
        model: CommunityActivity,
        poemHelp: PoemHelp
    )
    abstract fun receivingNamePoet()
    abstract fun addAvatar(namePoet: String)
}