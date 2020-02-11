package com.volgoak.simpleweather.ui.selectCity

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

class SelectCityEventBus {
    val eventRelay = PublishRelay.create<SelectCityEvent>()
}

sealed class SelectCityEvent {
    object CitySelected : SelectCityEvent()
    object SelectionCanceled : SelectCityEvent()
}