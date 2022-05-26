package com.application.poem_poet.di.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.application.poem_poet.App
import com.application.poem_poet.db.SessionStore
import com.application.poem_poet.domain.CommunityUseCase
import com.application.poem_poet.domain.CommunityUseCaseImpl
import com.application.poem_poet.domain.MainUseCase
import com.application.poem_poet.domain.MainUseCaseImpl
import com.application.poem_poet.service.SessionStoreService
import com.application.poem_poet.service.SessionStoreServiceImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Module
class AppModule(private val myApplication: App) {
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
    fun providesMainUseCase(sessionStoreService: SessionStoreService): MainUseCase {
        return MainUseCaseImpl(sessionStoreService)
    }

    @AppScope
    @Provides
    fun providesDetailedUseCase(sessionStoreService: SessionStoreService): CommunityUseCase {
        return CommunityUseCaseImpl(sessionStoreService)
    }

    @Provides
    @AppScope
    fun provideSessionStoreService(sharedPreferences: SharedPreferences): SessionStoreService {
        return SessionStoreServiceImpl(SessionStore(sharedPreferences))
    }

    @Provides
    @AppScope
    fun provideDefaultSharedPreferences(@AppContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @AppScope
    @AppContext
    fun provideAppContext(): Context = myApplication.applicationContext
}