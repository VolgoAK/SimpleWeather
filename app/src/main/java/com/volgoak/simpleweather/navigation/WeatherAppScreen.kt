package com.volgoak.simpleweather.navigation

import com.volgoak.simpleweather.ui.emptyCity.EmptyCityFragment
import com.volgoak.simpleweather.ui.forecast.ForecastFragment
import com.volgoak.simpleweather.ui.selectCity.SelectCityFragment

abstract class WeatherAppScreen: CustomSupportScreen() {

}

class WeatherScreen(): WeatherAppScreen() {
    override fun createFragment() = ForecastFragment()
}

class SelectCityScreen(val openForecastWhenSelected: Boolean = false): WeatherAppScreen() {
    override fun createFragment() = SelectCityFragment.newInstance(openForecastWhenSelected)
}

class EmptyCityScreen: WeatherAppScreen() {
    override fun createFragment() = EmptyCityFragment()
}