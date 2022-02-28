package com.application.poem_poet.ui.main

import com.application.poem_poet.domain.MainUseCase
import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(private val mainUseCase: MainUseCase) :
    MvpPresenter<MainView>()