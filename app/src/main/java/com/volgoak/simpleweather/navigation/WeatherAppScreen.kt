package com.volgoak.simpleweather.navigation

import com.volgoak.simpleweather.ui.forecast.ForecastFragment

abstract class WeatherAppScreen: CustomSupportScreen() {

}

class WeatherScreen(): WeatherAppScreen() {
    override fun createFragment() = ForecastFragment()
}