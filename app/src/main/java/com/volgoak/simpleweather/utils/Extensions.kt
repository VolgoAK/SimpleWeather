package com.volgoak.simpleweather.utils

import com.volgoak.simpleweather.bean.ReadableWeather
import com.volgoak.simpleweather.bean.Weather

/**
 * Created by alex on 4/1/18.
 */

fun Weather.toReadableWeather() : ReadableWeather {
    return ReadableWeather(this.name, this.main.temp)
}