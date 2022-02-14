package com.smarteryo.poem_poet

import android.app.Application
import com.smarteryo.poem_poet.di.component.AppComponent
import com.smarteryo.poem_poet.di.component.DaggerAppComponent
import com.smarteryo.poem_poet.di.modul.app.AppModule

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