package com.volgoak.simpleweather.ui.forecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.volgoak.simpleweather.model.location.LocationRepository
import com.volgoak.simpleweather.model.location.UserCityRepository
import com.volgoak.simpleweather.model.weather.WeatherRepository
import com.volgoak.simpleweather.utils.*
import io.reactivex.disposables.SerialDisposable
import io.reactivex.rxkotlin.Singles
import timber.log.Timber

class ForecastViewModel(
        private val weatherRepository: WeatherRepository,
        private val schedulersProvider: SchedulersProvider,
        private val locationRepository: LocationRepository,
        private val userCityRepository: UserCityRepository
): ViewModel() {
    val stateLD = MutableLiveData<ForecastScreenState>()

    private val weatherDisposable = SerialDisposable()

    init {
        stateLD.value = ForecastScreenState.LocationPermissionRequired
    }

    private fun loadWeather() {
        userCityRepository.getSelectedCity()
                .toSingle()
                .flatMap {
                    Singles.zip(
                            weatherRepository.requestCurrentWeather(it.name),
                            weatherRepository.requestForecast(it.name)
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
            locationRepository.getUserCurrentLocation()
                    .flatMapCompletable { userCityRepository.saveSelectedCity(it) }
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