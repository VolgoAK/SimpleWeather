package com.volgoak.simpleweather

import dagger.Component
import javax.inject.Singleton

/**
 * Created by alex on 4/1/18.
 */
@Singleton
@Component(modules = [WeatherModule::class])
interface ApplicationComponent {

    fun inject(target: WeatherActivity)
}