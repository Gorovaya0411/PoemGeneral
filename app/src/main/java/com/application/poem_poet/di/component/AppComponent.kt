package com.application.poem_poet.di.component

import com.application.poem_poet.di.modul.app.AppModule
import com.application.poem_poet.di.modul.app.AppScope
import com.application.poem_poet.di.modul.ui.community.CommunityActivityModule
import com.application.poem_poet.di.modul.ui.community.CommunityActivitySubcomponent
import com.application.poem_poet.di.modul.ui.main.MainActivityModule
import com.application.poem_poet.di.modul.ui.main.MainActivitySubcomponent

import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun with(mainActivityModule: MainActivityModule): MainActivitySubcomponent
    fun with(communityActivityModule: CommunityActivityModule): CommunityActivitySubcomponent
}