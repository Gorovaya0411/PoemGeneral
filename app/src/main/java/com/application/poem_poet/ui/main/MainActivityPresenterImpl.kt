package com.application.poem_poet.ui.main

import com.application.poem_poet.domain.MainUseCase
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class MainActivityPresenterImpl @Inject constructor(
    private val mainUseCase: MainUseCase

) : MainActivityPresenter() {

}