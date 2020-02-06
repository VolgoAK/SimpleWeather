package com.volgoak.simpleweather.model.weather

import com.volgoak.simpleweather.bean.Forecast
import com.volgoak.simpleweather.bean.Weather
import io.reactivex.Single

/**
 * Created by alex on 4/1/18.
 */
//todo make interface
class WeatherRepository(private val weatherCacheProvider: WeatherCacheProvider) {

    fun requestCurrentWeather(city: String): Single<Weather> {
        return weatherCacheProvider.getCurrentWeather(city)
    }

    fun requestForecast(city: String): Single<Forecast> {
        return weatherCacheProvider.getForecast(city)
    }

    fun requestCurrentWeather(lon: Double, lat: Double): Single<Weather> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}