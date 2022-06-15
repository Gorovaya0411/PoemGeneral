package com.application.poem_poet.di.mainModule

import com.application.poem_poet.ui.general_navigation.my_poem.MyPoemPresenter
import com.application.poem_poet.ui.general_navigation.poets.navigation.poets.ListPoetsPresenter
import com.application.poem_poet.ui.main.MainPresenter
import dagger.Subcomponent

@Subcomponent(modules = [MainActivityModule::class])
@MainActivityScope
interface MainActivitySubComponent {
    val mainPresenter: MainPresenter
    val myPoemPresenter: MyPoemPresenter
}
