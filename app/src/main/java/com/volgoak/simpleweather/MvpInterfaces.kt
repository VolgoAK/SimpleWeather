package com.volgoak.simpleweather

import com.volgoak.simpleweather.bean.*
import io.reactivex.Single

/**
 * Created by alex on 4/1/18.
 */

interface MVP {
    interface View {
        fun setWeather(weather: ReadableWeather)
        fun setForecast(forecastList: List<DayForecast>)
        fun showError()
        fun showProgress()
        fun hideProgress()
        fun askLocationPermission()
    }

    interface Model {
        fun requestCurrentWeather(city : String) : Single<Weather>
        fun requestCurrentWeather(lon : Double, lat : Double) : Single<Weather>
        fun requestForecast(city : String) : Single<Forecast>
    }

    interface Presenter {
        fun requestWeather()
        fun setView(view : MVP.View?)
    }
}