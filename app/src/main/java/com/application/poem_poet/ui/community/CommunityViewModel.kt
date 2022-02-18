package com.application.poem_poet.ui.community

import com.application.poem_poet.domain.usecase.AppUseCase
import com.application.poem_poet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val appUseCase: AppUseCase
) : BaseViewModel()