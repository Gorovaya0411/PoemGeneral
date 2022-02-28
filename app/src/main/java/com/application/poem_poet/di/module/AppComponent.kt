package com.application.poem_poet.di.module

import com.application.poem_poet.di.detailedModule.CommunityActivityModule
import com.application.poem_poet.di.detailedModule.CommunityActivitySubComponent
import com.application.poem_poet.di.mainModule.MainActivityModule
import com.application.poem_poet.di.mainModule.MainActivitySubComponent
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainActivityModule: MainActivityModule): MainActivitySubComponent
    fun inject(communityActivityModule: CommunityActivityModule): CommunityActivitySubComponent
}