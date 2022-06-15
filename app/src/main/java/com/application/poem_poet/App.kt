package com.application.poem_poet

import android.app.Application
import androidx.room.Room
import com.application.poem_poet.di.module.AppComponent
import com.application.poem_poet.di.module.AppModule
import com.application.poem_poet.di.module.DaggerAppComponent
import com.application.poem_poet.room.AppDatabase

class App : Application() {
    companion object {
        private lateinit var database: AppDatabase
        lateinit var appComponent: AppComponent

        @JvmStatic
        fun getDatabase(): AppDatabase {
            return App.database
        }
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        App.database = Room.databaseBuilder(this, AppDatabase::class.java, "savePoem").build()
    }
}