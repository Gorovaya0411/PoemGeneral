package com.application.poem_poet.ui.general_navigation.profile

import androidx.fragment.app.DialogFragment
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.User
import com.application.poem_poet.ui.job_user.AdapterJobUser
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface ProfileView : MvpView {
    fun showElementsProfile(model: User?)
    fun workWithAvatar(model: User?)
    fun workWithStatus()
    fun workWithAddress()
    fun showDialog(model: DialogFragment)
    fun openAddActivity()
    fun goToJobUserFragment()
}

abstract class ProfilePresenterImpl : MvpPresenter<ProfileView>() {
    abstract fun addData()
    abstract fun receivingPoem(login: String)
    abstract fun changeUsernameAll(model: String)
    abstract fun receivingPoemCatalog(login: String, uid: String)
    abstract fun changeUsernameAllCatalog(model: String, uid: String)
    abstract fun receivingPoemPoems(login: String, uid: String)
    abstract fun changeUsernameAllPoem(model: String)
    abstract fun checkListMyJob()
    abstract fun openAddActivity()
}