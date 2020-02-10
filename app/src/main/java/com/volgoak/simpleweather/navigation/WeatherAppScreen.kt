package com.volgoak.simpleweather.navigation

import com.volgoak.simpleweather.ui.forecast.ForecastFragment
import com.volgoak.simpleweather.ui.selectCity.SelectCityFragment

abstract class WeatherAppScreen: CustomSupportScreen() {

}

class WeatherScreen(): WeatherAppScreen() {
    override fun createFragment() = ForecastFragment()
}

class SelectCityScreen(): WeatherAppScreen() {
    override fun createFragment() = SelectCityFragment()
}