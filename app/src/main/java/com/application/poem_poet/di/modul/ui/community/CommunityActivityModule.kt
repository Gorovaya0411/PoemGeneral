package com.application.poem_poet.di.modul.ui.community

import com.application.poem_poet.ui.community.CommunityActivityPresenter
import com.application.poem_poet.ui.community.CommunityActivityPresenterImpl
import com.application.poem_poet.domain.MainUseCase
import dagger.Module
import dagger.Provides

@Module
class CommunityActivityModule {

    @Provides
    @CommunityActivityScope
    fun provideCommunityActivityPresenter(mainUseCase: MainUseCase): CommunityActivityPresenter {
        return CommunityActivityPresenterImpl(mainUseCase)
    }
}