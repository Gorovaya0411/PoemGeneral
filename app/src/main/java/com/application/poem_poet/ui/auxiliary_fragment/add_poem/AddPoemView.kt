package com.application.poem_poet.ui.auxiliary_fragment.add_poem

import android.widget.Button
import android.widget.CheckBox
import com.application.poem_poet.model.PoemHelp
import com.application.poem_poet.model.WorkAddUser
import com.application.poem_poet.ui.community.CommunityActivity
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface AddPoemView : MvpView {
    fun workWithAutoTxt(array: MutableList<String>)
    fun openGeneralPoets()
}

abstract class AddPoemViewImpl : MvpPresenter<AddPoemView>() {
    abstract fun receivingNamePoet()
    abstract fun addPoem(
        checkAdd: Boolean,
        model: CommunityActivity,
        poemAnswer: PoemHelp,
        addPoemCheckBox: CheckBox,
        addPoemAddBtn: Button
    ): Boolean
    abstract fun addUser(): WorkAddUser
}