package com.smarteryo.poem_poet.di.modul.app

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import com.smarteryo.poem_poet.data.service.SessionStoreService
import com.smarteryo.poem_poet.data.service.SessionStoreServiceImpl
import com.smarteryo.poem_poet.db.SessionStore
import com.smarteryo.poem_poet.MyApplication
import com.smarteryo.poem_poet.domain.MainUseCase
import com.smarteryo.poem_poet.domain.MainUseCaseImpl
import dagger.Module
import dagger.Provides

@Module()
class AppModule(private val myApplication: MyApplication) {

    @Provides
    @AppScope
    @AppContext
    fun provideAppContext(): Context = myApplication.applicationContext

    @Provides
    @AppScope
    fun provideSessionStoreService(sharedPreferences: SharedPreferences): SessionStoreService {
        return SessionStoreServiceImpl(SessionStore(sharedPreferences))
    }

    @Provides
    @AppScope
    fun provideConnectivityManager(@AppContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @AppScope
    fun provideDefaultSharedPreferences(@AppContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @AppScope
    fun provideMainUseCase(
        sessionStoreService: SessionStoreService
    ): MainUseCase {
        return MainUseCaseImpl(sessionStoreService)
    }
}