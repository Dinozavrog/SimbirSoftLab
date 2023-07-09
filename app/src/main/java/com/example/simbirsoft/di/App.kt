package com.example.simbirsoft.di

import android.app.Application
import com.example.simbirsoft.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import timber.log.Timber
import org.koin.core.context.GlobalContext.startKoin


class App: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                appModule,
                dataBaseModule,
                noteModule
            )
        }
    }
}