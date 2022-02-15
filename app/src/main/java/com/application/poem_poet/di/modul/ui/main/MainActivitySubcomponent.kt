package com.application.poem_poet.di.modul.ui.main

import com.application.poem_poet.ui.main.MainActivityPresenter
import dagger.Subcomponent

@Subcomponent(modules = [MainActivityModule::class])
@MainActivityScope
interface MainActivitySubcomponent {
    val presenter: MainActivityPresenter
}