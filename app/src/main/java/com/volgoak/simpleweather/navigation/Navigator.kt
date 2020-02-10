package com.example.singleactivityexample.navigation

import com.volgoak.simpleweather.navigation.WeatherAppScreen
import ru.terrakok.cicerone.Cicerone

class Navigator {
    private val cicerone by lazy { Cicerone.create() }

    fun setNavigator(navigator: MyAppNavigator) {
        cicerone.navigatorHolder.setNavigator(navigator)
    }

    fun removeNavigator() {
        cicerone.navigatorHolder.removeNavigator()
    }

    fun navigateTo(screen: WeatherAppScreen) {
        cicerone.router.navigateTo(screen)
    }

    fun backTo(screen: WeatherAppScreen) {
        cicerone.router.backTo(screen)
    }

    fun popScreen() {
        cicerone.router.exit()
    }

    fun setRootScreen(screen: WeatherAppScreen) {
        cicerone.router.newRootScreen(screen)
    }
}