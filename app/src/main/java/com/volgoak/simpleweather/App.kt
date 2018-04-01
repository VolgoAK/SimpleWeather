package com.volgoak.simpleweather

import android.app.Application

/**
 * Created by alex on 4/1/18.
 */
class App : Application(){

    lateinit var component : ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder()
                .weatherModule(WeatherModule())
                .build()
    }
}