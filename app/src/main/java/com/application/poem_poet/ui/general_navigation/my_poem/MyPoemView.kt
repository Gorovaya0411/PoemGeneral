package com.application.poem_poet.ui.general_navigation.my_poem

import androidx.fragment.app.DialogFragment
import com.application.poem_poet.model.PoemAnswer
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface MyPoemView : MvpView {
    fun showDialog(model: DialogFragment)
    fun showDeleteDialog(model: DialogFragment)
    fun openingNewActivity(model: PoemAnswer)
    fun openListPoemActivity()
    fun showToast()
    fun workWithAdapter(model: AdapterMyPoem)
}

abstract class MyPoemViewImpl : MvpPresenter<MyPoemView>() {
    abstract fun getData()
    abstract fun populateData(poems: MutableList<PoemAnswer?>)
    abstract fun openListPoemActivity()
    abstract fun onClick(model: PoemAnswer, mode: Int)
    abstract fun openingNewActivity(model: PoemAnswer)
    abstract fun functionDelete()
}