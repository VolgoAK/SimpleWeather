package com.volgoak.simpleweather.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.volgoak.simpleweather.bean.Forecast
import com.volgoak.simpleweather.bean.ReadableWeather
import com.volgoak.simpleweather.bean.Weather
import com.volgoak.simpleweather.model.location.LocationRepository
import com.volgoak.simpleweather.model.weather.WeatherRepository
import com.volgoak.simpleweather.utils.*
import io.reactivex.disposables.SerialDisposable
import io.reactivex.rxkotlin.Singles
import timber.log.Timber

class WeatherViewModel(
        private val weatherRepository: WeatherRepository,
        private val schedulersProvider: SchedulersProvider,
        private val locationRepository: LocationRepository
): ViewModel() {
    val stateLD = MutableLiveData<WeatherScreeState>()

    private val weatherDisposable = SerialDisposable()

    init {
        loadWeather()
    }

    private fun loadWeather() {
       //todo update location
        locationRepository.getLastKnownCity()
                .flatMap {
                    Singles.zip(
                            weatherRepository.requestCurrentWeather(it),
                            weatherRepository.requestForecast(it)
                    ).map {
                        WeatherScreeState.WeatherLoaded(it.first.toReadableWeather(), forecastToDailyForecast(it.second)!!)
                    }
                }
                .subscribeOn(schedulersProvider.io)
                .observeOn(schedulersProvider.ui)
                .doOnSubscribe { stateLD.value = WeatherScreeState.Loading }
                .subscribe( { state ->
                    stateLD.value = state
                }, { error ->
                    //todo handle error message
                    Timber.e(error)
                    stateLD.value = WeatherScreeState.Error("Error")
                }) into weatherDisposable
    }

    fun onPermissionResult(granted: Boolean) {
        //todo implement
    }

    override fun onCleared() {
        super.onCleared()
        weatherDisposable.dispose()
    }
}