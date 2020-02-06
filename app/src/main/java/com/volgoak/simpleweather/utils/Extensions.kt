package com.volgoak.simpleweather.utils

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.volgoak.simpleweather.bean.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.SerialDisposable

/**
 * Created by alex on 4/1/18.
 */

fun Weather.toReadableWeather(): ReadableWeather {
    val weather = ReadableWeather(this.name, this.main.temp, this.main.tempMin, this.main.tempMax)
    val description = this.weather?.get(0)?.description
    if (description != null) weather.description = description
    val icon = this.weather?.get(0)?.icon
    if (icon != null) weather.icon = icon
    return weather
}

fun Main.toReadableWeather(): ReadableWeather {
    return ReadableWeather(temp = this.temp, min = this.tempMin, max = this.tempMax)
}

fun ListItem.toReadableWeather(): ReadableWeather {
    val weather = this.main.toReadableWeather()
    weather.description = this.weather?.get(0)?.description ?: ""
    weather.icon = this.weather?.get(0)?.icon ?: ""
    weather.time = this.dt

    return weather
}

fun Forecast.toListOfReadableWeather(): List<ReadableWeather> {
    return this.list?.map {
        it.toReadableWeather()
    } ?: listOf()
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

infix fun Disposable.into(container: CompositeDisposable?) = container?.add(this)
infix fun Disposable.into(container: SerialDisposable?) = container?.set(this)

fun <T> LiveData<T>.observeSafe(owner: LifecycleOwner, block: (T) -> Unit) {
    this.observe(owner, Observer {
        it?.let { block(it) }
    })
}

inline fun <reified T : Any> Activity.getExtraNotNull(key: String, default: T? = null): T {
    val value = intent?.extras?.get(key)
    return requireNotNull(if (value is T) value else default) { key }
}

inline fun <reified T : Any> Activity.getExtra(key: String, default: T? = null): T? {
    val value = intent?.extras?.get(key)
    return if (value is T) value else default
}

inline fun <reified T : Any> Activity.extra(key: String, default: T? = null) = lazy {
    getExtra(key, default)
}

inline fun <reified T : Any> Activity.extraNotNull(key: String, default: T? = null) = lazy {
    getExtraNotNull(key, default)
}

inline fun <reified T : Any> Fragment.getExtra(key: String, default: T? = null): T? {
    val value = arguments?.get(key)
    return if (value is T) value else default
}

inline fun <reified T : Any> Fragment.getExtraNotNull(key: String, default: T? = null): T {
    val value = arguments?.get(key)
    return requireNotNull(if (value is T) value else default) { key }
}

inline fun <reified T : Any> Fragment.extra(key: String, default: T? = null) = lazy {
    getExtra(key, default)
}

inline fun <reified T : Any> Fragment.extraNotNull(key: String, default: T? = null) = lazy {
    getExtraNotNull(key, default)
}