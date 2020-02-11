package com.volgoak.simpleweather.ui.forecast

import com.volgoak.simpleweather.bean.DayForecast
import com.volgoak.simpleweather.bean.ReadableWeather

sealed class ForecastScreenState {
    data class WeatherLoaded(
            val weather: ReadableWeather,
            val forecast: List<DayForecast>): ForecastScreenState()
    object Loading: ForecastScreenState()
    data class Error(val error: String): ForecastScreenState()
    object LocationPermissionRequired: ForecastScreenState()
}