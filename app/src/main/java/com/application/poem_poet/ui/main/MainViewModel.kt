package com.application.poem_poet.ui.main

import com.application.poem_poet.domain.usecase.AppUseCase
import com.application.poem_poet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appUseCase: AppUseCase
) : BaseViewModel() {
}