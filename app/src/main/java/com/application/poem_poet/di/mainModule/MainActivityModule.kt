package com.application.poem_poet.di.mainModule

import com.application.poem_poet.ui.main.MainPresenter
import dagger.Module

@Module
class MainActivityModule {
    @MainActivityScope
    fun providesMainActivityPresenter(): MainPresenter {
        return MainPresenter()
    }
}