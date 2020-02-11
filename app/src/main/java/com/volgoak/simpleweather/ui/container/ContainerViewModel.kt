package com.volgoak.simpleweather.ui.container

import androidx.lifecycle.ViewModel
import com.example.singleactivityexample.navigation.Navigator
import com.volgoak.simpleweather.model.location.UserCityRepository
import com.volgoak.simpleweather.navigation.EmptyCityScreen
import com.volgoak.simpleweather.navigation.WeatherScreen
import com.volgoak.simpleweather.utils.SchedulersProvider
import com.volgoak.simpleweather.utils.into
import io.reactivex.disposables.SerialDisposable
import timber.log.Timber

class ContainerViewModel(private val userCityRepository: UserCityRepository,
                         private val navigator: Navigator,
                         private val schedulers: SchedulersProvider) : ViewModel() {

    private val disposable = SerialDisposable()

    //should be called only at first creation of activity
    fun onCreatedFirstTime() {
        userCityRepository.getSelectedCity()
                .subscribeOn(schedulers.io)
                .observeOn(schedulers.ui)
                .subscribe({
                    navigator.setRootScreen(WeatherScreen())
                }, { error ->
                    Timber.e(error)
                }, {
                    navigator.setRootScreen(EmptyCityScreen())
                }) into disposable
    }
}