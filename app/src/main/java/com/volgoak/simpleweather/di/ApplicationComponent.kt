package com.volgoak.simpleweather.di

import com.volgoak.simpleweather.ui.WeatherActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by alex on 4/1/18.
 */
@Singleton
@Component(modules = [WeatherModule::class, ApplicationModule::class])
interface ApplicationComponent {

    fun inject(target: WeatherActivity)
}