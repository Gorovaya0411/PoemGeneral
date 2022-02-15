package com.application.poem_poet.di.modul.ui.community

import com.application.poem_poet.ui.community.CommunityActivityPresenter

import dagger.Subcomponent

@Subcomponent(modules = [CommunityActivityModule::class])
@CommunityActivityScope
interface CommunityActivitySubcomponent {
    val presenter: CommunityActivityPresenter
}