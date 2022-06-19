package com.application.poem_poet.ui.auxiliary_fragment.detailed_poem

import com.application.poem_poet.ui.community.CommunityActivity
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface DetailedPoemView : MvpView {
    fun workWithCheckbox()
    fun workWithLike()
    fun addFavouritesPoem()
    fun addMyAdded()
}

abstract class DetailedPoemViewImpl : MvpPresenter<DetailedPoemView>() {
    abstract fun savingValueCheckBoxAddPoem(model: String)
    abstract fun savingValueCheckBoxLike(model: String)
    abstract fun workCheckboxLike(
        isChecked: Boolean,
        like: Int,
        id: String,
        namePoet: String,
        uid: String,
        idUser: String
    )

    abstract fun workCheckboxAdd(isChecked: Boolean, id: String, con: CommunityActivity)
}