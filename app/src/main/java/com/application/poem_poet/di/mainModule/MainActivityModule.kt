package com.application.poem_poet.di.mainModule

import com.application.poem_poet.domain.MainUseCase
import com.application.poem_poet.ui.main.MainPresenter
import dagger.Module

@Module
class MainActivityModule {
    @MainActivityScope
    fun providesMainActivityPresenter(mainUseCase: MainUseCase): MainPresenter {
        return MainPresenter(mainUseCase)
    }
}