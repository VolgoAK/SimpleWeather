package com.volgoak.simpleweather.bean

import java.util.*

/**
 * Created by alex on 4/1/18.
 */
data class ReadableWeather(val city: String = "",
                           val temp: Double,
                           val min: Double,
                           val max: Double,
                           var icon: String = "01d",
                           var description: String = "",
                           var time: Long = 0)

data class DayForecast(val date: Date,
                       val temp: Double, val min: Double, val max: Double,
                       val icon: String = "01d",
                       val description: String = "")