package com.volgoak.simpleweather.bean

/**
 * Created by alex on 4/1/18.
 */
data class ReadableWeather(val city : String, val temp : Double, val min : Double,
                           val max : Double, var icon: String = "01d",
                           var description : String = "")