package com.volgoak.simpleweather.model

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.volgoak.simpleweather.bean.Weather
import com.volgoak.simpleweather.utils.PreferenceHelper
import com.volgoak.simpleweather.utils.PreferenceHelper.get
import com.volgoak.simpleweather.utils.PreferenceHelper.set
import timber.log.Timber

/**
 * Created by alex on 4/2/18.
 */
class WeatherDataBase(context: Context) : DataBase {

    private val preferences: SharedPreferences = PreferenceHelper.defaultPrefs(context)
    private val gson: Gson = GsonBuilder().create()

    override fun getWeather(): Weather? {
        val saved: String? = preferences[SAVED_WEATHER]
        return if (saved != null) {
            Timber.d("SAVED $saved ")
            gson.fromJson(saved, Weather::class.java)
        }
        else null
    }

    override fun getWeatherUpdateTime(): Long {
       return  preferences[WEATHER_UPDATE_TIME, Long.MAX_VALUE] ?: 0
    }

    override fun saveWeather(weather: Weather) {
        Timber.d("save weather into preferences ")
        val weatherJson : String = gson.toJson(weather)
        preferences[SAVED_WEATHER] = weatherJson
        preferences[WEATHER_UPDATE_TIME] = System.currentTimeMillis()
    }

    companion object {
        const val WEATHER_UPDATE_TIME = "weather_update_time"
        const val SAVED_WEATHER = "saved_weather"
    }
}