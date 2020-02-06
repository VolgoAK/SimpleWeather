package com.volgoak.simpleweather.ui.forecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.volgoak.simpleweather.model.location.LocationRepository
import com.volgoak.simpleweather.model.weather.WeatherRepository
import com.volgoak.simpleweather.utils.*
import io.reactivex.disposables.SerialDisposable
import io.reactivex.rxkotlin.Singles
import timber.log.Timber

class ForecastViewModel(
        private val weatherRepository: WeatherRepository,
        private val schedulersProvider: SchedulersProvider,
        private val locationRepository: LocationRepository
): ViewModel() {
    val stateLD = MutableLiveData<ForecastScreenState>()

    private val weatherDisposable = SerialDisposable()

    init {
        stateLD.value = ForecastScreenState.LocationPermissionRequired
    }

    private fun loadWeather() {
        locationRepository.getLastKnownCity()
                .flatMap {
                    Singles.zip(
                            weatherRepository.requestCurrentWeather(it),
                            weatherRepository.requestForecast(it)
                    ).map {
                        ForecastScreenState.WeatherLoaded(it.first.toReadableWeather(), forecastToDailyForecast(it.second)!!)
                    }
                }
                .subscribeOn(schedulersProvider.io)
                .observeOn(schedulersProvider.ui)
                .doOnSubscribe { stateLD.value = ForecastScreenState.Loading }
                .subscribe( { state ->
                    stateLD.value = state
                }, { error ->
                    //todo handle error message
                    Timber.e(error)
                    stateLD.value = ForecastScreenState.Error("Error")
                }) into weatherDisposable
    }

    fun onPermissionResult(granted: Boolean) {
        if(granted) {
            locationRepository.update()
                    .subscribeOn(schedulersProvider.io)
                    .observeOn(schedulersProvider.ui)
                    .subscribe( {
                        loadWeather()
                    }, { error ->
                        Timber.e(error)
                        stateLD.value = ForecastScreenState.Error("Error")
                    }) into weatherDisposable
        }
    }

    override fun onCleared() {
        super.onCleared()
        weatherDisposable.dispose()
    }
}