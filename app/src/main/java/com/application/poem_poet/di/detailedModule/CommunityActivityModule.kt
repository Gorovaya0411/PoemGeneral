package com.application.poem_poet.di.detailedModule

import com.application.poem_poet.ui.community.CommunityPresenter
import com.application.poem_poet.domain.CommunityUseCase
import dagger.Module

@Module
class CommunityActivityModule() {
    @CommunityActivityScope
    fun providerDetailedActivityPresenter(communityUseCase: CommunityUseCase): CommunityPresenter {
        return CommunityPresenter(communityUseCase)
    }
}