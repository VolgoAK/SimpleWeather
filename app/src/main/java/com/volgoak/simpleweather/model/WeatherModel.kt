package com.volgoak.simpleweather.model

import com.volgoak.simpleweather.MVP
import com.volgoak.simpleweather.bean.Forecast
import com.volgoak.simpleweather.bean.Weather
import com.volgoak.simpleweather.utils.Values
import io.reactivex.Single
import timber.log.Timber

/**
 * Created by alex on 4/1/18.
 */
class WeatherModel(val api: WeatherApi, val db: DataBase) : MVP.Model {

    override fun requestCurrentWeather(city: String): Single<Weather> {
        val savedWeather: Weather? = db.getWeather()
        val savedTime = db.getWeatherUpdateTime()
        val timeSinceSaved = System.currentTimeMillis() - savedTime

        //Return cached weather
        return if (savedWeather != null && savedWeather.name.equals(city, true) &&
                timeSinceSaved < CURRENT_WEATHER_FRESH_TIMER) {
            Timber.d("Return saved weather")
            Single.just(savedWeather)
        } else {
            Timber.d("Call api for weather ")
            api.getWeather(city, Values.weatherApiKey).doOnSuccess { t: Weather -> db.saveWeather(t) }
        }
    }

    override fun requestForecast(city: String): Single<Forecast> {
        val savedForecast: Forecast? = db.getForecast()
        val savedTime = db.getForecastUpdateTime()
        val timeSinceSaved = System.currentTimeMillis() - savedTime

        return if (savedForecast != null && savedForecast.city.name.equals(city, true) &&
                timeSinceSaved < FORECAST_FRESH_TIMER) {
            Timber.d("Return saved forecast")
            Single.just(savedForecast)
        } else {
            Timber.d("Call forecast from api")
            api.getForecast(city, Values.weatherApiKey).doOnSuccess { f: Forecast -> db.saveForecast(f) }
        }
    }

    companion object {
        const val CURRENT_WEATHER_FRESH_TIMER = 30 * 60 * 1000
        const val FORECAST_FRESH_TIMER = 12 * 60 * 60 * 1000
    }
}