package com.volgoak.simpleweather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.core.content.ContextCompat
import com.volgoak.simpleweather.bean.Forecast
import com.volgoak.simpleweather.bean.Weather
import com.volgoak.simpleweather.model.location.LocationModelImpl
import com.volgoak.simpleweather.utils.forecastToDailyForecast
import com.volgoak.simpleweather.utils.toReadableWeather
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*


/**
 * Created by alex on 4/1/18.
 */
class WeatherPresenter(private var model: MVP.Model,
                       private val subscribe: Scheduler = Schedulers.io(),
                       private val observe: Scheduler = AndroidSchedulers.mainThread(),
                       private val appContext: Context) : MVP.Presenter {

    private var view: MVP.View? = null
    private var weatherDisposable: Disposable? = null
    private var forecastDisposable: Disposable? = null

    private var locationModel = LocationModelImpl(appContext)

    override fun requestWeather() {
        if(ContextCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_COARSE_LOCATION)
          != PackageManager.PERMISSION_GRANTED) {
            view?.askLocationPermission()
        } else {
           locationModel.update()
        }

        locationModel.getLastKnownCity().observeForever( {
            it?.let {
                onCityReadyWeather(it)
                onCityReadyForecast(it)
            }
        })
    }

    private fun onCityReadyWeather(city : String) {
        weatherDisposable?.dispose()
        weatherDisposable = model.requestCurrentWeather(city)
                .subscribeOn(subscribe)
                .observeOn(observe)
                .subscribe(this::onWeatherReady, this::onWeatherError)
    }

    private fun onCityReadyForecast(city: String) {
        forecastDisposable?.dispose()
        forecastDisposable = model.requestForecast(city)
                .subscribeOn(subscribe)
                .observeOn(observe)
                .subscribe(this::onForecastReady, this::onWeatherError)
    }

    override fun setView(view: MVP.View?) {
        this.view = view
    }

    private fun onWeatherReady(weather: Weather) {
        val readableWeather = weather.toReadableWeather()
        view?.setWeather(readableWeather)
    }

    private fun onForecastReady(forecast: Forecast) {
        val forecastList = forecastToDailyForecast(forecast)
        if (forecastList != null) {
            view?.setForecast(forecastList)
        }
    }

    private fun onWeatherError(error: Throwable) {
        view?.showError()
        error.printStackTrace()
        Timber.e(error)
    }

}