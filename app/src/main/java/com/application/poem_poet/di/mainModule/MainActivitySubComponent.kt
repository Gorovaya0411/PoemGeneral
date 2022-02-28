package com.application.poem_poet.di.mainModule

import com.application.poem_poet.ui.main.MainPresenter
import dagger.Subcomponent

@Subcomponent(modules = [MainActivityModule::class])
@MainActivityScope
interface MainActivitySubComponent {
    val presenter: MainPresenter
}
