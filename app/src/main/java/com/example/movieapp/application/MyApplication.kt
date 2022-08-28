package com.example.movieapp.application

import android.app.Application
import com.example.data.di.dataUtilsModule
import com.example.data.di.networkModule
import com.example.data.di.repositoryModule
import com.example.movieapp.BuildConfig
import com.example.movieapp.di.allFeaturesModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoinDi()
    }

    private fun initKoinDi() {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    networkModule,
                    dataUtilsModule,
                    repositoryModule,
                    *allFeaturesModules.toTypedArray(),
                )
            )
        }
    }
}