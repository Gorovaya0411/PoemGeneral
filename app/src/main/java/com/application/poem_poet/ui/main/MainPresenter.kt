package com.application.poem_poet.ui.main

import com.application.poem_poet.domain.MainUseCase
import javax.inject.Inject

class MainPresenter @Inject constructor(private val mainUseCase: MainUseCase) :
    MainActivityPresenter() {

    override fun getUidUser(): String? {
        return mainUseCase.saveUidUser
    }

    override fun setUidUser(uid: String?) {
        mainUseCase.saveUidUser = uid
    }
}