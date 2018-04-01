package com.volgoak.simpleweather

import com.volgoak.simpleweather.bean.Weather
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
    private var disposable : Disposable? = null


    override fun requestWeather() {
        disposable?.dispose()
        disposable = model.requestCurrentWeather("mumbai")
                .subscribeOn(subscribe)
                .observeOn(observe)
                .subscribe(this::onWeatherReady, this::onWeatherError)
    }

    override fun setView(view: MVP.View?) {
        this.view = view
    }

    private fun onWeatherReady(weather : Weather) {
        val readableWeather = weather.toReadableWeather()
        view?.setWeather(readableWeather)
    }

    private fun onWeatherError(error:Throwable) {
        view?.showError()
        error.printStackTrace()
        Timber.e(error)
    }
}