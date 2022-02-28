package com.application.poem_poet.ui.job_user

import androidx.fragment.app.DialogFragment
import com.application.poem_poet.model.PoemAnswer
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface JobUserView : MvpView {
    fun openDialog(model: DialogFragment)
    fun openAddActivity()
    fun openingNewActivity(model: PoemAnswer)
    fun openDeleteDialog(model: DialogFragment)
    fun toast()
//    fun workWithAdapter(model: AdapterJobUser)
}