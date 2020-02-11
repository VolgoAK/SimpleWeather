package com.volgoak.simpleweather.model.location

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import io.reactivex.Single
import timber.log.Timber

/**
 * Created by alex on 4/8/18.
 */
class LocationRepository(private val fusedLocationClient: FusedLocationProviderClient,
                         private val geocoder: Geocoder) {

    @SuppressLint("MissingPermission")
    fun getUserCurrentLocation(): Single<City> {
        return Single.create { emitter ->
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val city = readLocation(it)
                    emitter.onSuccess(city)
                } ?: run { emitter.onError(RuntimeException("Can't get user location")) }
            }.addOnFailureListener { exception ->
                emitter.onError(exception)
            }
        }
    }

    private fun readLocation(location: Location): City {
        val adresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        var city = ""

        for (adress in adresses) {
            city = adress.locality
            if (city.isNotEmpty()) break
        }

        return City(city, location.latitude, location.longitude)
    }

    fun searchCity(query: String): Single<List<City>> {
        return Single.create { emitter ->
            val addresses = geocoder.getFromLocationName(query, 20)
            addresses.filter { it.getAddressLine(0).isNullOrEmpty().not() }
                    .map { City(it.getAddressLine(0), it.latitude, it.longitude) }
                    .apply { emitter.onSuccess(this) }
        }
    }
}