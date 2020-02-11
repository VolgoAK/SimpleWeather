package com.volgoak.simpleweather

import android.app.Application
import com.volgoak.simpleweather.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Created by alex on 4/1/18.
 */
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        /*component = DaggerApplicationComponent.builder()
                .weatherModule(WeatherModule())
                .applicationModule(ApplicationModule(this))
                .build()*/

        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, viewModelsModule))
        }

        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

}