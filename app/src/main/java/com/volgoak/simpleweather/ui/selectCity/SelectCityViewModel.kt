package com.volgoak.simpleweather.ui.selectCity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.singleactivityexample.navigation.Navigator
import com.volgoak.simpleweather.model.location.City
import com.volgoak.simpleweather.model.location.LocationRepository
import com.volgoak.simpleweather.model.location.UserCityRepository
import com.volgoak.simpleweather.navigation.WeatherScreen
import com.volgoak.simpleweather.utils.SchedulersProvider
import com.volgoak.simpleweather.utils.into
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.SerialDisposable
import timber.log.Timber

class SelectCityViewModel(
        private val startForecastWhenSelected: Boolean,
        private val userCityRepository: UserCityRepository,
        private val locationRepository: LocationRepository,
        private val schedulersProvider: SchedulersProvider,
        private val navigator: Navigator,
        private val selectCityEventBus: SelectCityEventBus): ViewModel() {

    val citiesLd = MutableLiveData<List<City>>()

    private val searchDisposable = SerialDisposable()
    private val disposables = CompositeDisposable()

    fun searchAddress(query: String) {
        locationRepository.searchCity(query)
                .subscribeOn(schedulersProvider.io)
                .observeOn(schedulersProvider.ui)
                .subscribe({ cities ->
                    citiesLd.value = cities
                }, { error ->
                    Timber.e(error)
                    //todo handle error
                }) into searchDisposable
    }

    fun onCityClicked(city: City) {
        userCityRepository.saveSelectedCity(city)
                .subscribeOn(schedulersProvider.io)
                .observeOn(schedulersProvider.ui)
                .subscribe({
                    selectCityEventBus.eventRelay.accept(SelectCityEvent.CitySelected)
                    if(startForecastWhenSelected) {
                        navigator.setRootScreen(WeatherScreen())
                    } else {
                        navigator.popScreen()
                    }
                }, { Timber.e(it)}) into disposables
    }

    override fun onCleared() {
        super.onCleared()
        searchDisposable.dispose()
        disposables.dispose()
    }
}