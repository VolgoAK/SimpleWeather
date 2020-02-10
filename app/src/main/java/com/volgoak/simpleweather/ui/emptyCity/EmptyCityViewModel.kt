package com.volgoak.simpleweather.ui.emptyCity

import androidx.lifecycle.ViewModel
import com.example.singleactivityexample.navigation.Navigator
import com.volgoak.simpleweather.model.location.LocationRepository
import com.volgoak.simpleweather.model.location.UserCityRepository
import com.volgoak.simpleweather.navigation.SelectCityScreen
import com.volgoak.simpleweather.navigation.WeatherScreen
import com.volgoak.simpleweather.utils.SchedulersProvider
import com.volgoak.simpleweather.utils.SingleLiveEvent
import com.volgoak.simpleweather.utils.into
import io.reactivex.disposables.SerialDisposable
import timber.log.Timber

class EmptyCityViewModel(
        private val userCityRepository: UserCityRepository,
        private val locationRepository: LocationRepository,
        private val navigator: Navigator,
        private val schedulers: SchedulersProvider) : ViewModel() {

    val checkPermissionsLD = SingleLiveEvent<Unit>()
    private val getLocationDisposable = SerialDisposable()

    fun onSelectCityClicked() {
        navigator.setRootScreen(SelectCityScreen())
    }

    fun onCurrentLocationClicked() {
        checkPermissionsLD.value = Unit
    }

    fun onLocationPermissionGranted() {
        locationRepository.getUserCurrentLocation()
                .flatMapCompletable { userCityRepository.saveSelectedCity(it) }
                .subscribeOn(schedulers.io)
                .observeOn(schedulers.ui)
                .subscribe({
                    navigator.setRootScreen(WeatherScreen())
                }, {
                    Timber.e(it)
                }) into getLocationDisposable
    }

    override fun onCleared() {
        super.onCleared()
        getLocationDisposable.dispose()
    }
}