package com.smarteryo.poem_poet.di.component

import com.smarteryo.poem_poet.di.modul.app.AppModule
import com.smarteryo.poem_poet.di.modul.app.AppScope
import com.smarteryo.poem_poet.di.modul.ui.main.MainActivityModule
import com.smarteryo.poem_poet.di.modul.ui.main.MainActivitySubcomponent

import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun with(mainActivityModule: MainActivityModule): MainActivitySubcomponent
}