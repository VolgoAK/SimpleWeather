package com.volgoak.simpleweather.utils

import android.annotation.SuppressLint
import com.volgoak.simpleweather.bean.DayForecast
import com.volgoak.simpleweather.bean.Forecast
import com.volgoak.simpleweather.bean.ListItem
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

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

fun getDayIconUrl(type: String):String {
    var newType = type

    if(type.last() == 'n') {
        newType = type.substring(0, type.length - 2) + 'd'
    }

    return getIconUrl(newType)
}

fun forecastToDailyForecast(forecast: Forecast) : List<DayForecast>? {
    return forecast.list?.groupBy { forecastDateToDate(it.dtTxt).day }
            ?.values?.map { dayListToWeather(it) }

}

@SuppressLint("SimpleDateFormat")
fun forecastDateToDate(date : String) : Date {
    Timber.d("Parse date $date")
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return dateFormat.parse(date)
}

fun dayListToWeather(list : List<ListItem>) : DayForecast {
    val max : Double = list.maxBy { it.main.tempMax }?.main?.tempMax ?: 0.0
    val min : Double = list.minBy { it.main.tempMin }?.main?.tempMin ?: 0.0

    val temp = (max + min) /2

    val date = forecastDateToDate(list[0].dtTxt)

    val icon = list[0].weather?.get(0)?.icon ?: "01d"
    val description = list[0].weather?.get(0)?.description ?: "clear"

    return DayForecast(date, temp, min, max, icon, description)
}

val dateFormat = SimpleDateFormat("EE")

