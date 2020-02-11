package com.volgoak.simpleweather.model.weather

import com.nytimes.android.external.store3.base.impl.MemoryPolicy
import com.nytimes.android.external.store3.base.impl.StoreBuilder
import com.volgoak.simpleweather.bean.Forecast
import com.volgoak.simpleweather.bean.Weather

class WeatherCacheProvider(
        private val api: WeatherApi,
        private val cacheExpireTime: Long = 3600 * 3, //3 hours
        private val appId: String
) {

    private val memoryPolicy = MemoryPolicy.builder()
            .setExpireAfterWrite(cacheExpireTime)
            .build()

    private val currentWeatherStore by lazy {
        StoreBuilder.key<String, Weather>()
                .fetcher { key -> api.getWeather(key, appId)}
                .memoryPolicy(memoryPolicy)
                .open()
    }

    private val forecastStore by lazy {
        StoreBuilder.key<String, Forecast>()
                .fetcher { key -> api.getForecast(key, appId)}
                .memoryPolicy(memoryPolicy)
                .open()
    }

    fun getCurrentWeather(city: String) = currentWeatherStore.get(city)

    fun getForecast(city: String) = forecastStore.get(city)


}