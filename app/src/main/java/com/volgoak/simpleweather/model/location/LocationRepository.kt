package com.volgoak.simpleweather.model.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.volgoak.simpleweather.utils.PreferenceHelper
import com.volgoak.simpleweather.utils.PreferenceHelper.get
import com.volgoak.simpleweather.utils.PreferenceHelper.set
import io.reactivex.Completable
import io.reactivex.Single
import timber.log.Timber
import java.util.*

/**
 * Created by alex on 4/8/18.
 */
class LocationRepository(val context: Context) {

    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val preferenceHelper = PreferenceHelper.defaultPrefs(context)

    fun getLocation(): Single<Pair<Double, Double>> {
        return Single.create { emitter ->
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let { emitter.onSuccess(readLocation(it)) }
                        ?: run { emitter.onError(RuntimeException("Location is null")) }
            }.addOnFailureListener { exception ->
                emitter.onError(exception)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun update(): Completable {
        return Completable.create { emitter ->
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let { readLocation(it) }
                emitter.onComplete()
            }.addOnFailureListener { exception ->
                emitter.onError(exception)
            }
        }
    }

    fun readLocation(location: Location): Pair<Double, Double> {
        Timber.d("latitide ${location.latitude} longitude ${location.longitude}")
        val geocoder = Geocoder(context, Locale.US)
        val adresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        var city = ""

        for (adress in adresses) {
            Timber.d("adress ${adress.locality}")
            city = adress.locality
            if (city.isNotEmpty()) break
        }

        if (city.isNotEmpty()) {
            saveCity(city)
        }

        val position = Pair(location.latitude, location.longitude)
        saveLocation(position)
        return position
    }

    fun getLastKnownCity(): Single<String> {
        return Single.create { emitter ->
            preferenceHelper[CITY_PREF, "Moscow"]?.let {
                emitter.onSuccess(it)
            } ?: run {
                emitter.onError(RuntimeException("City is null"))
            }
        }
    }

    private fun saveCity(city: String) {
        preferenceHelper[CITY_PREF] = city
    }

    private fun saveLocation(location: Pair<Double, Double>) {
        preferenceHelper[LATITUDE_PREF] = location.first
        preferenceHelper[LONGITUDE_PREF] = location.second
    }

    companion object {
        const val CITY_PREF = "last_known_city"
        const val LATITUDE_PREF = "last_known_latitude"
        const val LONGITUDE_PREF = "last_known_longitude"
    }
}