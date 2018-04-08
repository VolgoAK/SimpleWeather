package com.volgoak.simpleweather

import com.volgoak.simpleweather.bean.Forecast
import com.volgoak.simpleweather.bean.Weather
import com.volgoak.simpleweather.utils.forecastToDailyForecast
import com.volgoak.simpleweather.utils.toListOfReadableWeather
import com.volgoak.simpleweather.utils.toReadableWeather
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by alex on 4/1/18.
 */
class WeatherPresenter(private var model: MVP.Model,
                       private val subscribe: Scheduler = Schedulers.io(),
                       private val observe: Scheduler = AndroidSchedulers.mainThread()) : MVP.Presenter {

    private var view: MVP.View? = null
    private var weatherDisposable : Disposable? = null
    private var forecastDisposable : Disposable? = null


    override fun requestWeather() {
        weatherDisposable?.dispose()
        weatherDisposable = model.requestCurrentWeather("mumbai")
                .subscribeOn(subscribe)
                .observeOn(observe)
                .subscribe(this::onWeatherReady, this::onWeatherError)
    }

    override fun requestForecast() {
        forecastDisposable?.dispose()
        forecastDisposable = model.requestForecast("moscow")
                .subscribeOn(subscribe)
                .observeOn(observe)
                .subscribe(this::onForecastReady, this::onWeatherError)
    }

    override fun setView(view: MVP.View?) {
        this.view = view
    }

    private fun onWeatherReady(weather : Weather) {
        val readableWeather = weather.toReadableWeather()
        view?.setWeather(readableWeather)
    }

    private fun onForecastReady(forecast : Forecast) {
        val forecastList = forecastToDailyForecast(forecast)
        if(forecastList != null) {
            view?.setForecast(forecastList)
        }
    }

    private fun onWeatherError(error:Throwable) {
        view?.showError()
        error.printStackTrace()
        Timber.e(error)
    }


}