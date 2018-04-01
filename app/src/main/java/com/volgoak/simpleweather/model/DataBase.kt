package com.volgoak.simpleweather.model

import com.volgoak.simpleweather.bean.Weather

/**
 * Created by alex on 4/2/18.
 */
interface DataBase {
    fun getWeather() : Weather?
    fun getWeatherUpdateTime() : Long

    fun saveWeather(weather: Weather)
}