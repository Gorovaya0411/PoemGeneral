package com.application.poem_poet.ui.main

import com.application.poem_poet.domain.MainUseCase
import com.application.poem_poet.model.User
import javax.inject.Inject

class MainPresenter @Inject constructor(private val mainUseCase: MainUseCase) :
    MainActivityPresenterImpl() {

    override fun getSaveUser(): User {
        return mainUseCase.saveUser
    }

    override fun setSaveUser(user: User) {
        mainUseCase.saveUser = user
    }
}