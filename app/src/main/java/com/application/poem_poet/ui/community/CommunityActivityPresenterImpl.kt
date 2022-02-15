package com.application.poem_poet.ui.community

import com.application.poem_poet.domain.MainUseCase
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class CommunityActivityPresenterImpl @Inject constructor(
    private val mainUseCase: MainUseCase

) : CommunityActivityPresenter() {

}