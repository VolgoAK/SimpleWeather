package com.volgoak.simpleweather.model

import com.volgoak.simpleweather.MVP
import com.volgoak.simpleweather.bean.Weather
import com.volgoak.simpleweather.utils.Values
import io.reactivex.Single
import timber.log.Timber

/**
 * Created by alex on 4/1/18.
 */
class WeatherModel(val api: WeatherApi, val db: DataBase) : MVP.Model{

    override fun requestCurrentWeather(name: String): Single<Weather> {
        val savedWeather : Weather? = db.getWeather()
        val savedTime = db.getWeatherUpdateTime()
        val timeSinceSaved = System.currentTimeMillis() - savedTime

        //Return cached weather
        if(savedWeather != null && savedWeather.name.equals(name, true) &&
                timeSinceSaved < CURRENT_WEATHER_UPDATE_LIMIT ) {
            Timber.d("Return saved weather")
            return Single.just(savedWeather)
        } else {
            Timber.d("Call api for weather ")
            return api.getWeather(name, Values.weatherApiKey).doOnSuccess { t: Weather -> db.saveWeather(t) }
        }
    }

    companion object {
        const val CURRENT_WEATHER_UPDATE_LIMIT = 30 * 60 * 1000
    }
}