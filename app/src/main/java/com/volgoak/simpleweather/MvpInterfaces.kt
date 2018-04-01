package com.volgoak.simpleweather

import com.volgoak.simpleweather.bean.ReadableWeather
import com.volgoak.simpleweather.bean.Weather
import io.reactivex.Single

/**
 * Created by alex on 4/1/18.
 */

interface MVP {
    interface View {
        fun setWeather(weather: ReadableWeather)
        fun showError()
        fun showProgress()
        fun hideProgress()
    }

    interface Model {
        fun requestCurrentWeather(city : String) : Single<Weather>
    }

    interface Presenter {
        fun requestWeather()
        fun setView(view : MVP.View?)
    }
}