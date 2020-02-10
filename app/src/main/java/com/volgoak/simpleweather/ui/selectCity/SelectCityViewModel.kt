package com.volgoak.simpleweather.ui.selectCity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.volgoak.simpleweather.model.location.City
import com.volgoak.simpleweather.model.location.LocationRepository
import com.volgoak.simpleweather.utils.SchedulersProvider
import com.volgoak.simpleweather.utils.into
import io.reactivex.disposables.SerialDisposable
import timber.log.Timber

class SelectCityViewModel(
        private val locationRepository: LocationRepository,
        private val schedulersProvider: SchedulersProvider): ViewModel() {

    val citiesLd = MutableLiveData<List<City>>()

    private val searchDisposable = SerialDisposable()

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
}