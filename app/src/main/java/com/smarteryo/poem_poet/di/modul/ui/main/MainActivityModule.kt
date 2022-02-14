package com.smarteryo.poem_poet.di.modul.ui.main

import com.smarteryo.poem_poet.domain.MainUseCase
import com.smarteryo.poem_poet.ui.main.MainActivityPresenter
import com.smarteryo.poem_poet.ui.main.MainActivityPresenterImpl
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