package com.application.poem_poet.di.modul.ui.main

import com.application.poem_poet.domain.MainUseCase
import com.application.poem_poet.ui.main.MainActivityPresenter
import com.application.poem_poet.ui.main.MainActivityPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    @MainActivityScope
    fun provideMainActivityPresenter(mainUseCase: MainUseCase): MainActivityPresenter {
        return MainActivityPresenterImpl(mainUseCase)
    }
}