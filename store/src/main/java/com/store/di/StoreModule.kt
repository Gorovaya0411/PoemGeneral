package com.store.di

import android.content.Context
import android.content.SharedPreferences
import com.store.data.service.SessionStoreService
import com.store.data.service.SessionStoreServiceImpl
import com.store.sessionstore.SessionStore
import com.store.util.PreferenceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StoreModule {

    @Provides
    @Singleton
    fun secureSharedPreferences(@ApplicationContext context: Context) =
        PreferenceHelper.securePrefs(context)

    @Provides
    @Singleton
    fun sessionStore(sharedPreferences: SharedPreferences) = SessionStore(sharedPreferences)

    @Provides
    @Singleton
    fun sessionStoreService(sessionStore: SessionStore): SessionStoreService =
        SessionStoreServiceImpl(sessionStore)
}