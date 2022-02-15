package com.application.poem_poet

import android.app.Application
import com.application.poem_poet.di.component.AppComponent
import com.application.poem_poet.di.component.DaggerAppComponent
import com.application.poem_poet.di.modul.app.AppModule

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }
}