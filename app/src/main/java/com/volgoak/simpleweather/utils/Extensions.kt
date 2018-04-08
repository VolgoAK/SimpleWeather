package com.volgoak.simpleweather.utils

import android.view.View
import com.volgoak.simpleweather.bean.*

/**
 * Created by alex on 4/1/18.
 */

fun Weather.toReadableWeather(): ReadableWeather {
    val weather = ReadableWeather(this.name, this.main.temp, this.main.tempMin, this.main.tempMax)
    val description = this.weather?.get(0)?.description
    if (description != null) weather.description = description
    val icon = this.weather?.get(0)?.icon
    if (icon != null) weather.icon = icon
    return weather
}

fun Main.toReadableWeather(): ReadableWeather {
    return ReadableWeather(temp = this.temp, min = this.tempMin, max = this.tempMax)
}

fun ListItem.toReadableWeather(): ReadableWeather {
    val weather = this.main.toReadableWeather()
    weather.description = this.weather?.get(0)?.description ?: ""
    weather.icon = this.weather?.get(0)?.icon ?: ""
    weather.time = this.dt

    return weather
}

fun Forecast.toListOfReadableWeather(): List<ReadableWeather> {
    return this.list?.map{
        it.toReadableWeather()
    } ?: listOf()
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}