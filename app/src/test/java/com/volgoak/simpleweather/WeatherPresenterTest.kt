package com.volgoak.simpleweather

import com.volgoak.simpleweather.bean.Main
import com.volgoak.simpleweather.bean.Weather
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherPresenterTest {

    @Mock
    lateinit var view: MVP.View
    @Mock
    lateinit var model: MVP.Model

    lateinit var presetner: WeatherPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        val weather = Weather(name = "Volgograd", main = Main(temp = 20.0), weather = null)
        `when`(model.requestCurrentWeather("volgograd")).thenReturn(Single.just(weather))

        presetner = WeatherPresenter(model, subscribe = Schedulers.io(), observe = Schedulers.io())
        presetner.setView(view)
    }

    @Test
    fun testPresenterCallViewWhenWeatherReady() {
        presetner.requestWeather()
        verify(view, timeout(5000).times(1)).setWeather(any())
    }

    @Test
    fun testErrorCall() {
        `when`(model.requestCurrentWeather("volgograd")).thenReturn(Single.error(RuntimeException()))
        presetner.requestWeather()
        verify(view, timeout(5000).times(0)).setWeather(any())
        verify(view, timeout(5000).times(1)).showError()
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T
}