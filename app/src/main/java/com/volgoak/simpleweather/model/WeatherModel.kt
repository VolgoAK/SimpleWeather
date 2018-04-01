package com.volgoak.simpleweather.model

import com.volgoak.simpleweather.MVP
import com.volgoak.simpleweather.bean.Weather
import com.volgoak.simpleweather.utils.Values
import io.reactivex.Single

/**
 * Created by alex on 4/1/18.
 */
class WeatherModel(val api: WeatherApi) : MVP.Model{

    override fun requestCurrentWeather(name: String): Single<Weather> {
        return api.getWeather(name, Values.weatherApiKey)
    }
}