package com.volgoak.simpleweather.ui

import com.volgoak.simpleweather.bean.DayForecast
import com.volgoak.simpleweather.bean.Forecast
import com.volgoak.simpleweather.bean.ReadableWeather

sealed class WeatherScreeState {
    data class WeatherLoaded(
            val weather: ReadableWeather,
            val forecast: List<DayForecast>): WeatherScreeState()
    object Loading: WeatherScreeState()
    data class Error(val error: String): WeatherScreeState()
    object LocationPermissionRequired: WeatherScreeState()
}