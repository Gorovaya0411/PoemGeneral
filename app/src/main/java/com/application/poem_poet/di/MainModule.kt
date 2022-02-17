package com.application.poem_poet.di

import com.application.poem_poet.domain.implementation.AppRepositoryImpl
import com.application.poem_poet.domain.repository.AppRepository
import com.application.poem_poet.domain.usecase.AppUseCase
import com.application.poem_poet.domain.usecase.AppUseCaseImpl
import com.store.data.service.SessionStoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideAppRepository(
        sessionStoreService: SessionStoreService
    ): AppRepository = AppRepositoryImpl(sessionStoreService)

    @Provides
    fun provideAppUseCase(
        appRepository: AppRepository,
        sessionStoreService: SessionStoreService
    ): AppUseCase = AppUseCaseImpl(sessionStoreService, appRepository)
}