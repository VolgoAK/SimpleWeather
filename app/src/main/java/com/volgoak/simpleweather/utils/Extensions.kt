package com.volgoak.simpleweather.utils

import com.volgoak.simpleweather.bean.ReadableWeather
import com.volgoak.simpleweather.bean.Weather

/**
 * Created by alex on 4/1/18.
 */

fun Weather.toReadableWeather() : ReadableWeather {
    val weather = ReadableWeather(this.name, this.main.temp)
    val icon = this.weather?.get(0)?.icon
    if(icon != null) weather.icon = icon
    return weather
}