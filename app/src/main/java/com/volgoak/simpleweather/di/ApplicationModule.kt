package com.volgoak.simpleweather.di

import com.volgoak.simpleweather.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by alex on 4/2/18.
 */

@Module
class ApplicationModule(val application : App) {

    @Provides
    @Singleton
    fun provedeApp() : App = application
}