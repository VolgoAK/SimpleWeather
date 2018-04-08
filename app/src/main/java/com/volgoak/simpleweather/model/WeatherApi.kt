package com.volgoak.simpleweather.model

import com.volgoak.simpleweather.bean.Forecast
import com.volgoak.simpleweather.bean.Weather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by alex on 4/1/18.
 */
interface WeatherApi {
    @GET(value = "weather")
    fun getWeather(@Query("q") city: String,
                   @Query("appid") appId: String,
                   @Query("units") unit: String = "metric",
                   @Query("lang") lang : String = "ru"): Single<Weather>

    @GET(value = "forecast")
    fun getForecast(@Query("q") city: String,
                    @Query("appid") appId: String,
                    @Query("units") unit: String = "metric",
                    @Query("lang") lang : String = "ru") : Single<Forecast>
}