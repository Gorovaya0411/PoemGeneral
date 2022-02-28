package com.application.poem_poet

import android.app.Application
import com.application.poem_poet.di.module.AppComponent
import com.application.poem_poet.di.module.AppModule
import com.application.poem_poet.di.module.DaggerAppComponent

class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule()).build()
    }
}