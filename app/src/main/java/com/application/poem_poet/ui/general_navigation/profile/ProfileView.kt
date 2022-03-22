package com.application.poem_poet.ui.general_navigation.profile

import androidx.fragment.app.DialogFragment
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface ProfileView : MvpView {
    fun showDialog(model: DialogFragment)
    fun openAddActivity()
    fun goToJobUserFragment()
}

abstract class ProfilePresenterImpl : MvpPresenter<ProfileView>() {
    abstract fun receivingPoem(login: String)
    abstract fun changeUsernameAll(model: String)
    abstract fun receivingPoemCatalog(login: String, uid: String)
    abstract fun changeUsernameAllCatalog(model: String, uid: String)
    abstract fun receivingPoemPoems(login: String, uid: String)
    abstract fun changeUsernameAllPoem(model: String)
    abstract fun openAddActivity()
}