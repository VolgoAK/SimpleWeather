package com.volgoak.simpleweather

import android.app.Application
import timber.log.Timber

/**
 * Created by alex on 4/1/18.
 */
class App : Application(){

    lateinit var component : ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder()
                .weatherModule(WeatherModule())
                .applicationModule(ApplicationModule(this))
                .build()

        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

}