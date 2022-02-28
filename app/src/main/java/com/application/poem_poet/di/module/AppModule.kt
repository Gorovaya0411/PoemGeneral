package com.application.poem_poet.di.module

import com.application.poem_poet.repository.CommunityRepository
import com.application.poem_poet.repository.MainRepository
import com.application.poem_poet.domain.CommunityUseCase
import com.application.poem_poet.domain.MainUseCase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


@Module
class AppModule {
    @AppScope
    @Provides
    fun provideOKHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(1200, TimeUnit.SECONDS)
            .connectTimeout(1200, TimeUnit.SECONDS)
            .build()
    }


    @AppScope
    @Provides
    fun providesMainUseCase(mainRepository: MainRepository): MainUseCase =
        MainUseCase(mainRepository)

    @AppScope
    @Provides
    fun providesDetailedUseCase(charactersDetailedRepository: CommunityRepository): CommunityUseCase =
        CommunityUseCase(charactersDetailedRepository)
}