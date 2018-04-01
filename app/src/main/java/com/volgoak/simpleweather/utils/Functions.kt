package com.volgoak.simpleweather.utils

/**
 * Created by alex on 4/1/18.
 */

fun getIconUrl(type: String): String {
    var image: String = when (type) {
        "01d" -> "clear_day.png"
        "01n" -> "clear_night.png"
        "02d" -> "few_clouds_day.png"
        "02n" -> "few_clouds_night.png"
        "03d", "04d" -> "clouds_day.png"
        "03n", "04n" -> "clouds_night.png"
        "09d" -> "rain_day.png"
        "09n" -> "rain_night.png"
        "10d" -> "small_rain_day.png"
        "10n" -> "small_rain_night.png"
        "11d" -> "storm_day.png"
        "11n" -> "storm_night.png"
        "13d" -> "snow_day.png"
        "13n" -> "snow_night.png"
        "50d", "50n" -> "mist.png"

        else -> "clear_day.png"
    }

    return "file:///android_asset/$image"
}