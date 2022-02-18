package com.application.poem_poet.ui.general_navigation.profile

import androidx.fragment.app.DialogFragment
import com.application.poem_poet.model.User
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface ProfileView : MvpView {
    fun showElementsProfile(model: User?)
    fun workWithStatus()
    fun workWithAddress()
    fun workWithAvatar(model: User?)
    fun showDialog(model: DialogFragment)
    fun openAddActivity()
}