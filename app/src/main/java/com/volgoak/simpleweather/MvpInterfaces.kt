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
    }

    interface Model {
        fun requestCurrentWeather(city : String) : Single<Weather>
        fun requestForecast(city : String) : Single<Forecast>
    }

    interface Presenter {
        fun requestWeather()
        fun requestForecast()
        fun setView(view : MVP.View?)
    }
}