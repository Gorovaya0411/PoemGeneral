package com.application.poem_poet.di.detailedModule

import com.application.poem_poet.ui.community.CommunityPresenter
import com.application.poem_poet.di.mainModule.MainActivityScope
import dagger.Subcomponent

@Subcomponent(modules = [CommunityActivityModule::class])
@MainActivityScope
interface CommunityActivitySubComponent {
    val presenter: CommunityPresenter
}
