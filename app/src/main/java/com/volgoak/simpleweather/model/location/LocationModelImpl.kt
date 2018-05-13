package com.volgoak.simpleweather.model.location

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.volgoak.simpleweather.utils.PreferenceHelper
import com.volgoak.simpleweather.utils.PreferenceHelper.get
import com.volgoak.simpleweather.utils.PreferenceHelper.set
import timber.log.Timber
import java.util.*

/**
 * Created by alex on 4/8/18.
 */
class LocationModelImpl(val context: Context) : LocationModel {

    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    private val preferenceHelper = PreferenceHelper.defaultPrefs(context)

    private var locationData: MutableLiveData<Pair<Double, Double>> = MutableLiveData()

    private val cityData: MutableLiveData<String> = MutableLiveData()

    override fun getLocation(): LiveData<Pair<Double, Double>> {

        return locationData
    }

    @SuppressLint("MissingPermission")
    override fun update() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let { readLocation(it) }
        }.addOnFailureListener { exception ->
            exception.printStackTrace()
        }

    }

    private fun readLocation(location: Location) {
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
            cityData.postValue(city)
        }

        val position = Pair<Double, Double>(location.latitude, location.longitude)
        saveLocation(position)
        locationData.postValue(position)
    }

    override fun getLastKnownCity(): LiveData<String> {
        val city: String = preferenceHelper[CITY_PREF, "Moscow"]!!
        cityData.value = city

        //for test delete!!
        val longitude : Double? = preferenceHelper[LONGITUDE_PREF]
        Timber.d("saved longitude $longitude")


        return cityData
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