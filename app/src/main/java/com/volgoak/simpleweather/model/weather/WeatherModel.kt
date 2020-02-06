package com.volgoak.simpleweather.model.weather

import com.volgoak.simpleweather.MVP
import com.volgoak.simpleweather.bean.Forecast
import com.volgoak.simpleweather.bean.Weather
import io.reactivex.Single

/**
 * Created by alex on 4/1/18.
 */
class WeatherModel(private val weatherCacheProvider: WeatherCacheProvider) : MVP.Model {

    override fun requestCurrentWeather(city: String): Single<Weather> {
        return weatherCacheProvider.getCurrentWeather(city)
    }

    override fun requestForecast(city: String): Single<Forecast> {
        return weatherCacheProvider.getForecast(city)
    }

    override fun requestCurrentWeather(lon: Double, lat: Double): Single<Weather> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val CURRENT_WEATHER_FRESH_TIMER = 30 * 60 * 1000
        const val FORECAST_FRESH_TIMER = 12 * 60 * 60 * 1000
    }
}